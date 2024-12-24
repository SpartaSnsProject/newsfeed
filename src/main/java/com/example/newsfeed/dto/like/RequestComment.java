package com.example.newsfeed.dto.like;

import lombok.Getter;
import lombok.Setter;

@Getter
public class RequestComment {

    Long postId;

    Long commentId;

    String contents;

    public RequestComment(Long postId, Long commentId, String contents) {
        this.postId = postId;
        this.commentId = commentId;
        this.contents = contents;
    }
}
