package com.example.newsfeed.service;

import com.example.newsfeed.dto.RequestUser;
import com.example.newsfeed.dto.ResponseFriend;
import com.example.newsfeed.entity.Friend;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.repository.FriendRepository;
import com.example.newsfeed.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendServiceImpl implements FriendService{
    FriendRepository friendRepository;

    UserRepository userRepository;
    public FriendServiceImpl(FriendRepository friendRepository, UserRepository userRepository) {
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addFriend(RequestUser RequestFollowingUser, String followUserEmail) {
        User followUser = userRepository.findByEmail(followUserEmail).orElseThrow(() -> new RuntimeException("유저를 못찾음"));
        User followingUser = userRepository.findById(RequestFollowingUser.getId()).orElseThrow(() -> new RuntimeException("유저를 못찾음"));
        friendRepository.save(new Friend(followUser, followingUser));
    }

    @Override
    public List<ResponseFriend> getFollowing(String followEmail) {
        User followUser = userRepository.findByEmail(followEmail).orElseThrow(() -> new RuntimeException("유저 못찾음"));
        return friendRepository.findAllByFollower(followUser).stream()
                .map(friend->new ResponseFriend(friend.getFollower(),friend.getFollowing()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseFriend> getFollower(String followingEmail) {
        User followingUser = userRepository.findByEmail(followingEmail).orElseThrow(() -> new RuntimeException("유저 못찾음"));
        return
                friendRepository.findAllByFollowing(followingUser)
                        .stream()
                .map(friend->new ResponseFriend(friend.getFollower(),friend.getFollowing()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFriend(String followEmail, Long followingId) {
        User followUser = userRepository.findByEmail(followEmail).orElseThrow(() -> new RuntimeException("유저를 못찾음"));
        User followingUser = userRepository.findById(followingId).orElseThrow(() -> new RuntimeException("유저를 못찾음"));

        friendRepository.deleteByFollowerAndFollowing(followUser,followingUser);
    }
}
