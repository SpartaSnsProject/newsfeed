package com.example.newsfeed.service;

import com.example.newsfeed.dto.RequestUser;
import com.example.newsfeed.entity.Friend;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.repository.FriendRepository;

import java.util.List;

public class FriendServiceImpl implements FriendService{
    FriendRepository friendRepository;

    UserRepository userRepository;
    public FriendServiceImpl(FriendRepository friendRepository, UserRepository userRepository) {
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addFriend(RequestUser RequestFollowingUser, Long followUserId) {
        User followUser = userRepository.findById(followUserId).orElseThrow(() -> new RuntimeException("유저를 못찾음"));
        User followingUser = userRepository.findById(RequestFollowingUser.getId()).orElseThrow(() -> new RuntimeException("유저를 못찾음"));
        friendRepository.save(new Friend(followUser, followingUser));
    }

    @Override
    public List<Friend> getFollowing(Long followId) {
        User followUser = userRepository.findById(followId).orElseThrow(() -> new RuntimeException("유저 못찾음"));
        return friendRepository.findByFollowerUser(followUser);
    }

    @Override
    public List<Friend> getFollower(Long followingId) {
        User followingUser = userRepository.findById(followingId).orElseThrow(() -> new RuntimeException("유저 못찾음"));
        return friendRepository.findByFollowingUser(followingUser);
    }

    @Override
    public void deleteFriend(Long followId, Long followingId) {
        User followUser = userRepository.findById(followId).orElseThrow(() -> new RuntimeException("유저를 못찾음"));
        User followingUser = userRepository.findById(followingId).orElseThrow(() -> new RuntimeException("유저를 못찾음"));

        friendRepository.deleteByFollowerUserAndFollowingUser(followUser,followingUser);
    }
}
