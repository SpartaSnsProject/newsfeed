package com.example.newsfeed.service;

import com.example.newsfeed.entity.Friend;
import com.example.newsfeed.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class friendService {

    private final FriendRepository friendRepository;

    public List<Long> findFriendsByUserId(Long userId) {
        // 팔로우한 친구 목록 찾기
        List<Friend> friends = friendRepository.findByFollowerId(userId);
        return friends.stream()
                .map(Friend::getTargetUserId)  // 친구의 userId 반환
                .collect(Collectors.toList());
    }
}
