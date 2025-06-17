package com.themoviedb.authenticator.jwt;

import com.themoviedb.authenticator.model.entity.Token;
import com.themoviedb.authenticator.model.exception.InvalidTokenException;
import com.themoviedb.authenticator.model.exception.UserNotFoundException;
import com.themoviedb.authenticator.repository.TokenRepository;
import com.themoviedb.authenticator.model.entity.User;
import com.themoviedb.authenticator.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${application.security.jwt.secret}")
    private String SECRET_KEY;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    public String getToken(Map<String, Object> extraClaims, User user) {
        return getToken(extraClaims, user, jwtExpiration);
    }

    public String getRefreshToken(Map<String, Object> extraClaims, User user) {
        return getToken(extraClaims, user, refreshExpiration);
    }

    private String getToken(Map<String, Object> extraClaims, User user, long expiration) {
        return Jwts
                .builder()
                .setId(user.getId().toString())
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        Token jwtToken = tokenRepository.findByToken(token).orElse(null);

        if (jwtToken == null || jwtToken.isExpired() || jwtToken.isRevoked()) {
            return false;
        }

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public User getUserFromToken(String authHeader) throws InvalidTokenException, UserNotFoundException {
        String token = extractToken(authHeader);
        final String username = getUsernameFromToken(token);

        if (username == null) throw new InvalidTokenException("Token inválido - Username");

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado - FindUsername"));

        if (!isTokenValid(token, user)) throw new InvalidTokenException("Token inválido o expirado");

        return user;
    }

    public void revokeAllUserTokens(User user) {
        final List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user);

        if (!validUserTokens.isEmpty()) {
            for (final Token token : validUserTokens) {
                token.setExpired(true);
                token.setRevoked(true);
            }
            tokenRepository.saveAll(validUserTokens);
        }
    }

    public void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    private String extractToken(String authHeader) throws InvalidTokenException {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Token inválido o ausente");
        }

        return authHeader.substring(7);
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

}
