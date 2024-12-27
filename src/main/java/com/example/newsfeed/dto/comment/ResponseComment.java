package com.example.newsfeed.dto.comment;

import com.example.newsfeed.dto.friend.ResponseFriend;
import com.example.newsfeed.entity.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseComment {

    String displayName;

    String contents;


    public static ResponseComment from(Comment comment) {
        return ResponseComment.builder()
                .displayName(comment.getPost().getUser().getDisplayName())
                .contents(comment.getContents())
                .build();
    }
}
