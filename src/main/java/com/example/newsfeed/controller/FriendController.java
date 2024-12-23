package com.example.newsfeed.controller;

import com.example.newsfeed.dto.RequestUser;
import com.example.newsfeed.dto.ResponseFriend;
import com.example.newsfeed.service.FriendService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, List<ResponseFriend>>> findFollowingUser(HttpSession session) {
        Long followId = (Long) session.getAttribute("id");
        List<ResponseFriend> following = friendSerivce.getFollowing(followId);
        Map<String, List<ResponseFriend>> response = new HashMap<>();
        response.put("followingUsers", following);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String, List<ResponseFriend>>> findFollower(HttpSession session) {
        Long followingId = (Long) session.getAttribute("id");
        List<ResponseFriend> follower = friendSerivce.getFollower(followingId);
        Map<String, List<ResponseFriend>> response = new HashMap<>();
        response.put("followers", follower);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFrined(@RequestHeader("Authorization") Long followingId, HttpSession session) {
        Long followId = (Long) session.getAttribute("id");
        friendSerivce.deleteFriend(followId, followingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
