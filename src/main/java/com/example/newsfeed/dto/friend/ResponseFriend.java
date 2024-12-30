package com.example.newsfeed.dto.friend;

import com.example.newsfeed.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseFriend {
    private final User follower;
    private final User following;


    public static ResponseFriend from(User follower,User following ) {
        return ResponseFriend.builder()
                .follower(follower)
                .following(following)
                .build();
    }
}
