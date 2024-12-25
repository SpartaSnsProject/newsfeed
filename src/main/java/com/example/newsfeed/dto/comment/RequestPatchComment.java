package com.example.newsfeed.dto.comment;

import lombok.Getter;

@Getter
public class RequestPatchComment {

    Long commentId;
    String contents;

    public RequestPatchComment(Long commentId, String contents) {
        this.commentId = commentId;
        this.contents = contents;
    }
}
