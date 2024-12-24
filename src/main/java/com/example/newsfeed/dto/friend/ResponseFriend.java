package com.example.newsfeed.dto.friend;

import com.example.newsfeed.entity.User;

public class ResponseFriend {
    User follower;
    User following;

    public ResponseFriend(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }
}