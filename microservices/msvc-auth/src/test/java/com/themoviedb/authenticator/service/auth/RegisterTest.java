package com.themoviedb.authenticator.service.auth;

import com.themoviedb.authenticator.model.entity.User;
import com.themoviedb.authenticator.model.request.RegisterRequest;
import com.themoviedb.authenticator.service.BaseAuthServiceTest;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class RegisterTest extends BaseAuthServiceTest {

    @Test
    public void testRegisterSuccess() {
        User user = getUser();

        RegisterRequest request = new RegisterRequest();
        request.setUsername(user.getUsername());
        request.setEmail(user.getEmail());

        when(userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(jwtService.getToken(Map.of("email", user.getEmail()), user)).thenReturn("token");
        when(jwtService.getRefreshToken(Map.of("email", user.getEmail()), user)).thenReturn("refreshToken");


    }
}
