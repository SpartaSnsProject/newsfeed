package com.example.newsfeed.exception;

import lombok.Getter;

@Getter
public class DuplicateUsernameException extends RuntimeException {
    public DuplicateUsernameException(String message) {
        super(message);
    }
}