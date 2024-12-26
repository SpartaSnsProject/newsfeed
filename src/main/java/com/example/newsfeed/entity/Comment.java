package com.example.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String displayName;

    @Setter
    String contents;

    @Column(name = "like_count")
    int commentLikeCount;

    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    Comment comment;


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
