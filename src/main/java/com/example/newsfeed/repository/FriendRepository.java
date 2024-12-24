package com.example.newsfeed.repository;

import com.example.newsfeed.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByFollowerId(Long userId);
}
