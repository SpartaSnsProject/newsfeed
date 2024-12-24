package com.example.newsfeed.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Post {

    @Id
    private Long id;

    public Post() {
    }

    public Post(Long id) {
        this.id = id;
    }
}
