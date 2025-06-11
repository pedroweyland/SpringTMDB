package com.themoviedb.authenticator.jwt;

import com.themoviedb.authenticator.repository.token.Token;
import com.themoviedb.authenticator.repository.token.TokenRepository;
import com.themoviedb.authenticator.repository.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
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

    public String getToken(Map<String, Object> extraClaims, User user) {
        return getToken(extraClaims, user, jwtExpiration);
    }

    public String getRefreshToken(Map<String, Object> extraClaims, User user) {
        return getToken(extraClaims, user, refreshExpiration);
    }

    private String getToken(Map<String,Object> extraClaims, User user, long expiration) {
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
        byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
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

        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    private Claims getAllClaims(String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token)
    {
        return getClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token)
    {
        return getExpiration(token).before(new Date());
    }

}
