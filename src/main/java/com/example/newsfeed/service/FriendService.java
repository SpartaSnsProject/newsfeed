package com.example.newsfeed.service;

import com.example.newsfeed.dto.RequestUser;
import com.example.newsfeed.dto.ResponseFriend;

import java.util.List;

public interface FriendService {
    void addFriend(RequestUser user, String id);

    void deleteFriend(String followId, Long followingId);

    List<ResponseFriend> getFollowing(String followId);

    List<ResponseFriend> getFollower(String followingId);

}
