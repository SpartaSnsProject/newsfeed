package com.example.newsfeed.exception.user;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException(String message) {
        super(message);
    }
}