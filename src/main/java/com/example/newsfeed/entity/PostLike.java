package com.example.newsfeed.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class PostLike {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;

    @Column(unique = true)
    Long userId;

    public PostLike(Post post, Long userId) {
        this.post = post;
        this.userId = userId;
    }
}
