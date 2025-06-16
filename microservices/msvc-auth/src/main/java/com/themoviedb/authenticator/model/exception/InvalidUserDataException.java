package com.themoviedb.authenticator.model.exception;

public class InvalidUserDataException extends Exception {
    public InvalidUserDataException(String message) {
        super(message);
    }
}
