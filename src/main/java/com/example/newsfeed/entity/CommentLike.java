package com.example.newsfeed.entity;

import jakarta.persistence.*;

@Entity
public class CommentLike {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    Comment comment;

    public CommentLike() {
    }

    public CommentLike(Comment comment) {
        this.comment = comment;
    }
}
