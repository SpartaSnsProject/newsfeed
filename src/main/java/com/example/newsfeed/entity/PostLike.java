package com.example.newsfeed.entity;

import jakarta.persistence.*;

@Entity
public class PostLike {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;
}
