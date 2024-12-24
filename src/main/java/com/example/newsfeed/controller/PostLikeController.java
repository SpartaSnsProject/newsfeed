package com.example.newsfeed.controller;

import com.example.newsfeed.dto.like.PostLikeResponse;
import com.example.newsfeed.dto.like.RequestPost;
import com.example.newsfeed.service.PostLikeService;
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

    @PostMapping
    public ResponseEntity<PostLikeResponse> addPostLike(@RequestBody RequestPost post , HttpSession session) {
        Long id = (Long) session.getAttribute("id");
        postLikeService.addPostLike(id,post);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<Integer> getCommentLIke(@PathVariable("post_id") Long postLike) {
        int commentLike = postLikeService.getPostLike(postLike);
        return new ResponseEntity<>(commentLike,HttpStatus.OK);
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<Void> deletePostLike(@PathVariable("post_id") Long postId, HttpSession session) {
        Long id = (Long) session.getAttribute("id");
        postLikeService.deletePostLike(id,postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
