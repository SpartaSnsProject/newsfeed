package com.example.newsfeed.controller;

import com.example.newsfeed.dto.friend.ResponseFriend;
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

    @PostMapping("/{display_name}")
    public ResponseEntity<Void> addFriend(@PathVariable("display_name") String displayName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        friendService.addFriend(displayName,username);

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

    @DeleteMapping("/{display_name}")
    public ResponseEntity<Void> deleteFriend(@PathVariable("display_name") String displayName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        friendService.deleteFriend(username, displayName);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
