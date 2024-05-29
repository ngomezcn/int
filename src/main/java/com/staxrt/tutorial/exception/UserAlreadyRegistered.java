package com.staxrt.tutorial.exception;

public class UserAlreadyRegistered extends RuntimeException {
    public UserAlreadyRegistered(String message) {
        super(message);
    }
}

