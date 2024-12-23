package com.example.newsfeed.service;

import com.example.newsfeed.dto.RequestUser;
import com.example.newsfeed.entity.Friend;
import com.example.newsfeed.entity.User;

import java.util.List;

public interface FriendService {
    void addFriend(RequestUser user, Long id);

    void deleteFriend(Long followId, Long followingId);

    List<Friend> getFollowing(Long followId);

    List<Friend> getFollower(Long followingId);

}
