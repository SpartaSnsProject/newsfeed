package com.example.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

    public Friend(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }
}
