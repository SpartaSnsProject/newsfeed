package com.example.newsfeed.dto.comment;

import com.example.newsfeed.entity.Comment;

public class ResponseComment {

    String displayName;

    String contents;

    public ResponseComment(Comment save) {
        this.displayName = save.getDisplayName();
        this.contents = save.getContents();
    }
}
