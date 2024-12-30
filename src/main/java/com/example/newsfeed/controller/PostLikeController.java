package com.example.newsfeed.controller;


import com.example.newsfeed.service.PostLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post_like")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @Operation(summary = "게시물 좋아요 추가",
            description = "게시물에 좋아요를 추가합니다.",
            security = {@SecurityRequirement(name = "Bearer Authentication")})
    @PostMapping("/{post_id}")
    public ResponseEntity<Void> addPostLike(
            @PathVariable("post_id") Long postId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String username = userDetails.getUsername();
        postLikeService.addPostLike(username,postId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "게시물 좋아요 확인",
            description = "게시물에 좋아요를 확인합니다.",
            security = {@SecurityRequirement(name = "Bearer Authentication")})
    @GetMapping("/{post_id}")
    public ResponseEntity<Integer> getCommentLIke(
            @PathVariable("post_id") Long postLike
    ) {
        int commentLike = postLikeService.getPostLike(postLike);
        return new ResponseEntity<>(commentLike,HttpStatus.OK);
    }

    @Operation(summary = "게시물 좋아요 삭제",
            description = "게시물에 좋아요를 삭제합니다.",
            security = {@SecurityRequirement(name = "Bearer Authentication")})
    @DeleteMapping("/{post_id}")
    public ResponseEntity<Void> deletePostLike(
            @PathVariable("post_id") Long postId,
            @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        postLikeService.deletePostLike(username,postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
