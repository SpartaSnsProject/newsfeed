package com.example.newsfeed.controller;

import com.example.newsfeed.dto.RequestUser;
import com.example.newsfeed.dto.ResponseFriend;
import com.example.newsfeed.service.FriendService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/follow")
public class FriendController {

    FriendService friendService;

    public FriendController(FriendService friendSerivce) {
        this.friendService = friendSerivce;
    }

    @PostMapping
    public ResponseEntity<Void> addFriend(@RequestBody RequestUser user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        friendService.addFriend(user,username);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/following")
    public ResponseEntity<Map<String, List<ResponseFriend>>> findFollowingUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<ResponseFriend> following = friendService.getFollowing(username);
        Map<String, List<ResponseFriend>> response = new HashMap<>();
        response.put("followingUsers", following);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/follower")
    public ResponseEntity<Map<String, List<ResponseFriend>>> findFollower() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<ResponseFriend> follower = friendService.getFollower(username);
        Map<String, List<ResponseFriend>> response = new HashMap<>();
        response.put("followers", follower);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFrined(@RequestHeader("Authorization") Long followingId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        friendService.deleteFriend(username, followingId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
