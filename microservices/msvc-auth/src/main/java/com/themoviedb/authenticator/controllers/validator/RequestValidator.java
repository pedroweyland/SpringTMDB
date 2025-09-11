package com.themoviedb.authenticator.controllers.validator;

import com.themoviedb.authenticator.model.exception.InvalidUserDataException;
import com.themoviedb.authenticator.model.request.ListRequest;
import com.themoviedb.authenticator.model.request.LoginRequest;
import com.themoviedb.authenticator.model.request.RegisterRequest;
import org.springframework.util.StringUtils;

public class RequestValidator {

    public static void validateRegisterRequest(RegisterRequest request) throws InvalidUserDataException {

        if (!StringUtils.hasText(request.getUsername())) {
            throw new InvalidUserDataException("Username is required.");
        }

        if (request.getUsername().length() < 4) {
            throw new InvalidUserDataException("Username must be at least 4 characters long.");
        }

        if (!StringUtils.hasText(request.getEmail())) {
            throw new InvalidUserDataException("Email is required.");
        }

        if (!StringUtils.hasText(request.getPassword())) {
            throw new InvalidUserDataException("Password is required.");
        }

        if (request.getPassword().length() < 8) {
            throw new InvalidUserDataException("Password must be at least 8 characters long.");
        }
    }

    public static void validateLoginRequest(LoginRequest request) throws InvalidUserDataException {

        if (!StringUtils.hasText(request.getUsername())) {
            throw new InvalidUserDataException("Username is required.");
        }

        if (!StringUtils.hasText(request.getPassword())) {
            throw new InvalidUserDataException("Password is required.");
        }
    }

    public static void validateListRequest(ListRequest request) throws InvalidUserDataException {

        if (request.getIdMediaApi() == null || request.getIdMediaApi() <= 0) {
            throw new InvalidUserDataException("IdMediaApi is required.");
        }

        if (!StringUtils.hasText(request.getMediaType())) {
            throw new InvalidUserDataException("MediaType is required.");
        }
    }
}
