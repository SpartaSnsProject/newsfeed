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

    @Column(unique = true)
    Long userId;

    public CommentLike() {
    }

    public CommentLike(Long id) {
        this.id = id;
    }


    public CommentLike(Comment comment,Long userId) {
        this.comment = comment;
        this.userId = userId;
    }
}
