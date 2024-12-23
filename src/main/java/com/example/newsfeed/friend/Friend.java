package com.example.newsfeed.friend;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @OneToMany
    List<User> userIds = new ArrayList<>();
}
