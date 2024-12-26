package com.example.newsfeed.service;

import com.example.newsfeed.dto.friend.ResponseFriend;
import com.example.newsfeed.entity.Friend;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.repository.FriendRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserService userService;

    // private final UserRepository userRepository 리포지토리의존관계 제거 서비스로 의존관계 변경(한성우)

    public void addFriend(String displayName, String followUserEmail) {

        User followUser = userService.findByEmail(followUserEmail);

        User followingUser = userService.findByDisplayName(displayName);

        if (followUser.getId() == followingUser.getId()) {
            throw new RuntimeException("자기자신을 팔로우할수없음 익셉션");
        }

        followUser.upFollowingCount();
        followingUser.upFollowerCount();

        friendRepository.save(new Friend(followUser, followingUser));

        userService.save(followUser);
        userService.save(followingUser);

    }

    public List<ResponseFriend> getFollowing(String followEmail) {

        User followUser = userService.findByEmail(followEmail);

        return friendRepository.findAllByFollower(followUser).stream()
                .map(friend->ResponseFriend.from(friend.getFollower(),friend.getFollowing()))
                .collect(Collectors.toList());
    }

    public List<ResponseFriend> getFollower(String followingEmail) {
        User followingUser = userService.findByEmail(followingEmail);
        return
                friendRepository.findAllByFollowing(followingUser)
                        .stream()
                        .map(friend->ResponseFriend.from(friend.getFollower(),friend.getFollowing()))
                        .collect(Collectors.toList());
    }

    public void deleteFriend(String followEmail, String followingId) {
        User followUser = userService.findByEmail(followEmail);
        User followingUser = userService.findByDisplayName(followingId);

        friendRepository.deleteByFollowerAndFollowing(followUser,followingUser);

        followUser.downFollowingCount();
        followingUser.downFollowerCount();
        userService.save(followingUser);
        userService.save(followingUser);

    }
    public List<User> findSuggestion(String username) {
        User byEmail = userService.findByEmail(username);
        List<Long> numbers = new ArrayList<>();

        List<User> all = userService.findAll();

        int size = all.size();

        for (long i = 1; i < size; i++) {
            if (!(i ==byEmail.getId())) {
                numbers.add(i);
            }
        }

        Collections.shuffle(numbers);

        List<Long> list = numbers.subList(0, 3);

       return userService.findByIdIn(list);


    }
}