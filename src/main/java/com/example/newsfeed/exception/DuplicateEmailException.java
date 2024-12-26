package com.example.newsfeed.exception;

import lombok.Getter;

@Getter
public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
