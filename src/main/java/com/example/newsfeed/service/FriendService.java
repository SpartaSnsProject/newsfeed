package com.example.newsfeed.service;

import com.example.newsfeed.dto.ResponseFriend;

import java.util.List;

public interface FriendService {
    void addFriend(String user, String id);

    void deleteFriend(String followId, String followingId);

    List<ResponseFriend> getFollowing(String followId);

    List<ResponseFriend> getFollower(String followingId);

}
