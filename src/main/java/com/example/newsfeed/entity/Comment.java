package com.example.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String displayName;

    @Setter
    String contents;

    @Column(name = "like_count")
    int commentLikeCount;

    @ManyToOne
    @JoinColumn(name = "post_post")
    Post post;

    @ManyToOne
    @JoinColumn(name = "comment_comment")
    Comment comment;


    public Comment() {
    }

    public Comment(String displayName, Post post, String contents) {
        this.displayName = displayName;
        this.post = post;
        this.contents = contents;
    }

    public Comment(String displayName, Comment comment,String contents) {
        this.displayName= displayName;
        this.comment = comment;
        this.contents = contents;
    }

    public void upLikeCount(){
        this.commentLikeCount++;
    }
}
