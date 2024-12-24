package com.example.newsfeed.exception.post;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PostNotFoundException extends RuntimeException{
    private final String failReason;
    private final HttpStatus httpStatus;

    public PostNotFoundException(String failReason, HttpStatus httpStatus) {
        super(failReason);
        this.failReason = failReason;
        this.httpStatus = httpStatus;
    }
}
