package com.example.newsfeed.repository;

import com.example.newsfeed.entity.Friend;
import com.example.newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend,Long> {

    void deleteByFollowerAndFollowing(User followUser, User followingUser);

    List<Friend> findAllByFollower(User followUser);

    List<Friend> findAllByFollowing(User followingUser);
}
