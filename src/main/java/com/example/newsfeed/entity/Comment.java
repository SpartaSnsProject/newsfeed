package com.example.newsfeed.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Setter;

@Entity
public class Comment {
    @Id
    Long id;

    public Comment() {
    }

    public Comment(Long id) {
        this.id = id;
    }
}
