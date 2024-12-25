package com.example.newsfeed.dto.comment;

import com.example.newsfeed.dto.friend.ResponseFriend;
import com.example.newsfeed.entity.Comment;
import lombok.Builder;

@Builder
public class ResponseComment {

    String displayName;

    String contents;


    public static ResponseComment from(Comment comment) {
        return ResponseComment.builder()
                .displayName(comment.getDisplayName())
                .contents(comment.getContents())
                .build();
    }
}
