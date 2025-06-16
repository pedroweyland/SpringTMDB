package com.themoviedb.authenticator.model.exception;

public class MediaNotFoundException extends Exception{
    public MediaNotFoundException(String message) {
        super(message);
    }
}
