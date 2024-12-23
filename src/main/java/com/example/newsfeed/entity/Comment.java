package com.example.newsfeed.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Comment {
    @Id
    Long id;
}
