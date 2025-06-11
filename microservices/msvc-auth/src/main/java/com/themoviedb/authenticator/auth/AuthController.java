package com.themoviedb.authenticator.auth;

import com.themoviedb.authenticator.exception.UserAlreadyExistsException;
import com.themoviedb.authenticator.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) throws UserAlreadyExistsException {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping(value = "refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return ResponseEntity.ok(authService.refreshToken(authHeader));
    }

    @GetMapping(value = "validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return ResponseEntity.ok(authService.validateToken(authHeader));
    }
}
