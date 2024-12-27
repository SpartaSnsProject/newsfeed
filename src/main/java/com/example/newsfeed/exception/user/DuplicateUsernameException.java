package com.example.newsfeed.exception.user;

import lombok.Getter;

@Getter
public class DuplicateUsernameException extends RuntimeException {
    public DuplicateUsernameException(String message) {
        super(message);
    }
}