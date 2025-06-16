package com.themoviedb.gateway.handler;

import com.themoviedb.gateway.exception.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GatewayExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public Mono<ResponseEntity<CustomApiError>> handleResponseStatusException(ResponseStatusException ex) {
        CustomApiError error = new CustomApiError();
        error.setErrorCode(ex.getStatusCode().value());
        error.setErrorMessage(ex.getReason());

        return Mono.just(ResponseEntity.status(ex.getStatusCode()).body(error));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public Mono<ResponseEntity<CustomApiError>> handleTokenInvalid(InvalidTokenException ex) {
        CustomApiError error = new CustomApiError();
        error.setErrorCode(401);
        error.setErrorMessage(ex.getMessage());

        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error));
    }

}
