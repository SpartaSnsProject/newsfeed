package com.example.newsfeed.controller;

import com.example.newsfeed.dto.friend.ResponseFriend;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/follow")
public class FriendController {

    FriendService friendService;

    public FriendController(FriendService friendSerivce) {
        this.friendService = friendSerivce;
    }

    @Operation(summary = "팔로우추가",
            description = "팔로우를 추가합니다.",
            security = {@SecurityRequirement(name = "Bearer Authentication")})
    @PostMapping("/{display_name}")
    public ResponseEntity<Void> addFriend(
            @PathVariable("display_name") String displayName
    , @AuthenticationPrincipal UserDetails userDetails
    ) {
        String username = userDetails.getUsername();

        friendService.addFriend(displayName,username);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/suggestion")
    public ResponseEntity<List<User>> showSuggestion(
            @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        List<User> suggestion = friendService.findSuggestion(username);

        return new ResponseEntity<>(suggestion, HttpStatus.OK);
    }

    @Operation(summary = "팔로윙유저조회",
            description = "내가 팔로윙중인 유저를 찾습니다.",
            security = {@SecurityRequirement(name = "Bearer Authentication")})
    @GetMapping("/following")
    public ResponseEntity<Map<String, List<ResponseFriend>>> findFollowingUser(
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        String username = userDetails.getUsername();

        List<ResponseFriend> following = friendService.getFollowing(username);
        Map<String, List<ResponseFriend>> response = new HashMap<>();
        response.put("followingUsers", following);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "팔로워찾기",
            description = "나를 팔로잉중인 팔로워를 찾습니다.",
            security = {@SecurityRequirement(name = "Bearer Authentication")})
    @GetMapping("/follower")
    public ResponseEntity<Map<String, List<ResponseFriend>>> findFollower(@AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();

        List<ResponseFriend> follower = friendService.getFollower(username);
        Map<String, List<ResponseFriend>> response = new HashMap<>();
        response.put("followers", follower);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "팔로우삭제",
            description = "팔로우를 삭제합니다.",
            security = {@SecurityRequirement(name = "Bearer Authentication")})
    @DeleteMapping("/{display_name}")
    public ResponseEntity<Void> deleteFriend(
            @PathVariable("display_name") String displayName
            , @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();

        friendService.deleteFriend(username, displayName);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
