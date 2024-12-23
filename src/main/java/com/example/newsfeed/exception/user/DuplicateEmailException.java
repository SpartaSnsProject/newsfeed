package com.example.newsfeed.exception.user;

import lombok.Getter;

@Getter
public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
