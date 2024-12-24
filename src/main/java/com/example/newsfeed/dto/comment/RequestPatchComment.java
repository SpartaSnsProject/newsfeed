package com.example.newsfeed.dto.comment;

import lombok.Getter;

@Getter
public class RequestPatchComment {

    Long commentId;
    String contents;
}
