package com.themoviedb.authenticator.exception;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
