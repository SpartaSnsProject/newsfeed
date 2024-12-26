package com.example.newsfeed.controller;


import com.example.newsfeed.service.PostLikeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post_like")
public class PostLikeController {

    private final PostLikeService postLikeService;

    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    @Operation(summary = "게시물좋아요추가",description = "게시물에 좋아요를 추가합니다.")
    @PostMapping("/{post_id}")
    public ResponseEntity<Void> addPostLike(@PathVariable("post_id") Long postId, HttpSession session) {
        Long id = (Long) session.getAttribute("id");
        postLikeService.addPostLike(id,postId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "게시물좋아요",description = "게시물에 좋아요를 추가합니다.")
    @GetMapping("/{post_id}")
    public ResponseEntity<Integer> getCommentLIke(@PathVariable("post_id") Long postLike) {
        int commentLike = postLikeService.getPostLike(postLike);
        return new ResponseEntity<>(commentLike,HttpStatus.OK);
    }

    @Operation(summary = "게시물좋아요삭제.",description = "게시물에 좋아요를 삭제합니다.")
    @DeleteMapping("/{post_id}")
    public ResponseEntity<Void> deletePostLike(@PathVariable("post_id") Long postId, HttpSession session) {
        Long id = (Long) session.getAttribute("id");
        postLikeService.deletePostLike(id,postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
