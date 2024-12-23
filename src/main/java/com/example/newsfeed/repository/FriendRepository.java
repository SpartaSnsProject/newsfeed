package com.example.newsfeed.repository;

import com.example.newsfeed.entity.Friend;
import com.example.newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend,Long> {

    void deleteByFollowerUserAndFollowingUser(User followUser, User followingUser);

    List<Friend> findByFollowerUser(User followUser);

    List<Friend> findByFollowingUser(User followingUser);
}
