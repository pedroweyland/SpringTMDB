package com.themoviedb.authenticator.service;

import com.themoviedb.authenticator.jwt.JwtService;
import com.themoviedb.authenticator.model.Role;
import com.themoviedb.authenticator.model.entity.User;
import com.themoviedb.authenticator.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseAuthServiceTest {

    @Mock
    protected JwtService jwtService;

    @Mock
    protected UserRepository userRepository;

    @Mock
    protected PasswordEncoder passwordEncoder;

    @Mock
    protected AuthenticationManager authenticationManager;

    @InjectMocks
    protected AuthService authService;

    @BeforeEach
    public void baseSetUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthService(userRepository, jwtService, passwordEncoder, authenticationManager);
    }

    protected User getUser() {
        return User.builder()
                .username("pedroweyland")
                .email("weylandpedro@gmail.com")
                .password("NotPassword")
                .role(Role.USER)
                .build();
    }
}
