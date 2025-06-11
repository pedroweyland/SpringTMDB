package com.themoviedb.authenticator.auth;

import com.themoviedb.authenticator.exception.UserAlreadyExistsException;
import com.themoviedb.authenticator.jwt.JwtService;
import com.themoviedb.authenticator.repository.token.Token;
import com.themoviedb.authenticator.repository.token.TokenRepository;
import com.themoviedb.authenticator.repository.user.Role;
import com.themoviedb.authenticator.repository.user.User;
import com.themoviedb.authenticator.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        String token = jwtService.getToken(Map.of("email", user.getEmail()), user);
        String refreshToken = jwtService.getRefreshToken(Map.of("email", user.getEmail()), user);

        revokeAllUserTokens(user);
        saveUserToken(user, token);

        return AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        final List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user);

        if (!validUserTokens.isEmpty()) {
            for (final Token token : validUserTokens) {
                token.setExpired(true);
                token.setRevoked(true);
            }
            tokenRepository.saveAll(validUserTokens);
        }
    }

    public AuthResponse register(RegisterRequest request) throws UserAlreadyExistsException {

        validateUserExists(request.getUsername(), request.getEmail());

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String token = jwtService.getToken(Map.of("email", user.getEmail()), user);
        String refreshToken = jwtService.getRefreshToken(Map.of("email", user.getEmail()), user);
        
        saveUserToken(user, token);

        return AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    private void validateUserExists(String username, String email) throws UserAlreadyExistsException {
        Optional<User> existingUser = userRepository.findByUsernameOrEmail(
                username, email);

        if (existingUser.isPresent()) {
            User user = existingUser.get();

            if (user.getUsername().equals(username)) {
                throw new UserAlreadyExistsException("Username already exists");
            } else {
                throw new UserAlreadyExistsException("Email already exists");
            }
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);

    }

    public AuthResponse refreshToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token inválido o ausente");
        }

        String refreshToken = authHeader.substring(7);
        String username = jwtService.getUsernameFromToken(refreshToken);

        if (username == null) {
            throw new IllegalArgumentException("Refresh Token inválido");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new RuntimeException("Refresh token inválido o expirado");
        }

        final String accessToken = jwtService.getToken(Map.of("email", user.getEmail()), user);

        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    public Boolean validateToken(String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token inválido o ausente");
        }
        String token = authHeader.substring(7);

        User user = userRepository.findByUsername(jwtService.getUsernameFromToken(token))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return jwtService.isTokenValid(token, user);
    }
}
