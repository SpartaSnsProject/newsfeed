package com.example.newsfeed.controller;

import com.example.newsfeed.dto.friend.ResponseFriend;
import com.example.newsfeed.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "팔로우추가",description = "팔로우를 추가합니다.")
    @PostMapping("/{display_name}")
    public ResponseEntity<Void> addFriend(@PathVariable("display_name") String displayName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        friendService.addFriend(displayName,username);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "팔로윙유저조회",description = "내가 팔로윙중인 유저를 찾습니다.")
    @GetMapping("/following")
    public ResponseEntity<Map<String, List<ResponseFriend>>> findFollowingUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<ResponseFriend> following = friendService.getFollowing(username);
        Map<String, List<ResponseFriend>> response = new HashMap<>();
        response.put("followingUsers", following);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "팔로워찾기",description = "나를 팔로잉중인 팔로워를 찾습니다.")
    @GetMapping("/follower")
    public ResponseEntity<Map<String, List<ResponseFriend>>> findFollower() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<ResponseFriend> follower = friendService.getFollower(username);
        Map<String, List<ResponseFriend>> response = new HashMap<>();
        response.put("followers", follower);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "팔로우삭제",description = "팔로우를 삭제합니다.")
    @DeleteMapping("/{display_name}")
    public ResponseEntity<Void> deleteFriend(@PathVariable("display_name") String displayName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        friendService.deleteFriend(username, displayName);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
