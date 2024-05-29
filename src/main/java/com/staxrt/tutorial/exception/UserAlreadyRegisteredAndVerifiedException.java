package com.staxrt.tutorial.exception;

public class UserAlreadyRegisteredAndVerifiedException extends RuntimeException {
    public UserAlreadyRegisteredAndVerifiedException(String message) {
        super(message);
    }
}
