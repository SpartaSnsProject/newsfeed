package com.example.newsfeed.dto.like;

import lombok.Getter;
import lombok.Setter;

@Getter
public class RequestComment {
    Long id;

    public RequestComment(Long id) {
        this.id = id;
    }
}
