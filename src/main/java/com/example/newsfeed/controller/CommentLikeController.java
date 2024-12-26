package com.example.newsfeed.controller;


import com.example.newsfeed.service.CommentLikeService;
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
@RequestMapping("/comment_likes")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;


    @Operation(summary = "댓글에 좋아요추가",
            description = "댓글에 좋아요를 추가합니다.",
            security = {@SecurityRequirement(name = "Bearer Authentication")})
    @PostMapping("/{comment_id}")
    public ResponseEntity<Void> addCommentLike(
            @PathVariable("comment_id") Long commentId,
            @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        commentLikeService.addCommentLike(username,commentId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "댓글좋아요갯수",
            description = "댓글에 좋아요 갯수를 확인합니다.",
            security = {@SecurityRequirement(name = "Bearer Authentication")})
    @GetMapping("/{comment_id}")
    public ResponseEntity<Integer> getCommentLIke(@PathVariable("comment_id") Long commentId) {
        int commentLike = commentLikeService.getCommentLike(commentId);
        return new ResponseEntity<>(commentLike,HttpStatus.OK);
    }

    @Operation(summary = "댓글에 좋아요 삭제",
            description = "댓글에 좋아요를 삭제합니다.",
            security = {@SecurityRequirement(name = "Bearer Authentication")})
    @DeleteMapping("/{comment_id}")
    public ResponseEntity<Void> deleteCommentLike(@PathVariable("comment_id") Long commentId,HttpSession session) {
        Long id = (Long) session.getAttribute("id");
        commentLikeService.deleteCommentLike(id,commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
