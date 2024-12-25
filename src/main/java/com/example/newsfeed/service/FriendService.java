package com.example.newsfeed.service;

import com.example.newsfeed.dto.friend.ResponseFriend;
import com.example.newsfeed.entity.Friend;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.repository.FriendRepository;
import com.example.newsfeed.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendService {
    private final FriendRepository friendRepository;

    private final UserRepository userRepository;

    public void addFriend(String displayName, String followUserEmail) {

        User followUser = userRepository.findByEmail(followUserEmail).orElseThrow(() -> new RuntimeException("유저를 못찾음"));

        User followingUser = userRepository.findByDisplayName(displayName).orElseThrow(() -> new RuntimeException("유저를 못찾음"));

        friendRepository.save(new Friend(followUser, followingUser));
    }

    public List<ResponseFriend> getFollowing(String followEmail) {
        User followUser = userRepository.findByEmail(followEmail).orElseThrow(() -> new RuntimeException("유저 못찾음"));
        return friendRepository.findAllByFollower(followUser).stream()
                .map(friend->ResponseFriend.from(friend.getFollower(),friend.getFollowing()))
                .collect(Collectors.toList());
    }

    public List<ResponseFriend> getFollower(String followingEmail) {
        User followingUser = userRepository.findByEmail(followingEmail).orElseThrow(() -> new RuntimeException("유저 못찾음"));
        return
                friendRepository.findAllByFollowing(followingUser)
                        .stream()
                .map(friend->ResponseFriend.from(friend.getFollower(),friend.getFollowing()))
                .collect(Collectors.toList());
    }

    public void deleteFriend(String followEmail, String followingId) {
        User followUser = userRepository.findByEmail(followEmail).orElseThrow(() -> new RuntimeException("유저를 못찾음"));
        User followingUser = userRepository.findByDisplayName(followingId).orElseThrow(() -> new RuntimeException("유저를 못찾음"));

        friendRepository.deleteByFollowerAndFollowing(followUser,followingUser);
    }
}
