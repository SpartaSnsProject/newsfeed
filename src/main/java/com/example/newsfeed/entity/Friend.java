package com.example.newsfeed.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "following_user")
    User following;

    @ManyToOne
    @JoinColumn(name = "follower_user")
    User follower;

    public Friend() {
    }

    public Friend(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }
}
