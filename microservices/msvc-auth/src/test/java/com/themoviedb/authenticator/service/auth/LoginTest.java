package com.themoviedb.authenticator.service.auth;

import com.themoviedb.authenticator.model.entity.User;
import com.themoviedb.authenticator.model.request.LoginRequest;
import com.themoviedb.authenticator.model.response.AuthResponse;
import com.themoviedb.authenticator.service.BaseAuthServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class LoginTest extends BaseAuthServiceTest {

    @Test
    public void loginTestSuccess() {
        User usuario = getUser();
        LoginRequest request = new LoginRequest();
        request.setUsername("pedroweyland");
        request.setPassword("NotPassword");

        when(userRepository.findByUsername(usuario.getUsername())).thenReturn(Optional.of(usuario));
        when(jwtService.getToken(Map.of("email", usuario.getEmail()), usuario)).thenReturn("token");
        when(jwtService.getRefreshToken(Map.of("email", usuario.getEmail()), usuario)).thenReturn("refreshToken");

        AuthResponse response = authService.login(request);

        verify(authenticationManager, times(1)).authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        verify(userRepository, times(1)).findByUsername(request.getUsername());
        verify(jwtService, times(1)).getToken(Map.of("email", usuario.getEmail()), usuario);
        verify(jwtService, times(1)).getRefreshToken(Map.of("email", usuario.getEmail()), usuario);
        verify(jwtService, times(1)).revokeAllUserTokens(usuario);
        verify(jwtService, times(1)).saveUserToken(usuario, "token");

        assertEquals(response.getAccessToken(), "token");
        assertEquals(response.getRefreshToken(), "refreshToken");

    }

    @Test
    public void loginTestBadCredentials() {
        LoginRequest request = new LoginRequest();
        request.setUsername("badUser");
        request.setPassword("badPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Bad credentials"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.login(request));
        assertEquals("Bad credentials", exception.getMessage());

        verifyNoInteractions(userRepository);
        verifyNoInteractions(jwtService);
    }
}
