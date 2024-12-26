package com.example.newsfeed.service;

import com.example.newsfeed.dto.friend.ResponseFriend;
import com.example.newsfeed.entity.Friend;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.exception.user.UserNotFoundException;
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
    private final UserService userService;
    private final UserRepository userRepository;

    public void addFriend(String displayName, String followUserEmail) {

        userService.findByEmail(followUserEmail);
        User followUser = userRepository.findByEmail(followUserEmail).orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));

        User followingUser = userRepository.findByDisplayName(displayName).orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));

        if (followUser.getId() == followingUser.getId()) {
            throw new RuntimeException("자기자신을 팔로우할수없음 익셉션");
        }
        friendRepository.save(new Friend(followUser, followingUser));

        followUser.upFollowingCount();
        followingUser.upFollowerCount();
    }

    public List<ResponseFriend> getFollowing(String followEmail) {
        User followUser = userRepository.findByEmail(followEmail).orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
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

        followUser.downFollowingCount();
        followingUser.downFollowerCount();
    }
}