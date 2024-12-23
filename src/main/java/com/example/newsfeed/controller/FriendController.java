package com.example.newsfeed.controller;

import com.example.newsfeed.dto.RequestUser;
import com.example.newsfeed.entity.Friend;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.service.FriendService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
public class FriendController {

    FriendService friendSerivce;

    public FriendController(FriendService friendSerivce) {
        this.friendSerivce = friendSerivce;
    }

    @PostMapping
    public ResponseEntity<Void> addFriend(@RequestBody RequestUser user, HttpSession session) {
        Long id = (Long) session.getAttribute("id");
        friendSerivce.addFriend(user,id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Friend>> findFollowingUser(HttpSession session) {
        Long followId = (Long) session.getAttribute("id");
        List<Friend> following = friendSerivce.getFollowing(followId);
        return new ResponseEntity<>(following, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Friend>> findFollower(HttpSession session) {
        Long followingId = (Long) session.getAttribute("id");
        friendSerivce.getFollower(followingId);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFrined(@RequestHeader("Authorization") Long followingId, HttpSession session) {
        Long followId = (Long) session.getAttribute("id");
        friendSerivce.deleteFriend(followId, followingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
