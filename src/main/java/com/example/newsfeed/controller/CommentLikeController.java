package com.example.newsfeed.controller;

import com.example.newsfeed.dto.like.RequestComment;
import com.example.newsfeed.service.CommentLikeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment_likes")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    public CommentLikeController(CommentLikeService commentLikeService) {
        this.commentLikeService = commentLikeService;
    }

    @PostMapping
    public ResponseEntity<Void> addCommentLike(@RequestBody RequestComment comment, HttpSession session) {
        Long id = (Long) session.getAttribute("id");
        commentLikeService.addCommentLike(id,comment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{comment_id}")
    public ResponseEntity<Integer> getCommentLIke(@PathVariable("comment_id") Long commentId) {
        int commentLike = commentLikeService.getCommentLike(commentId);
        return new ResponseEntity<>(commentLike,HttpStatus.OK);
    }

    @DeleteMapping("{comment_id}")
    public ResponseEntity<Void> deleteCommentLike(@PathVariable("comment_id") Long commentId,HttpSession session) {
        Long id = (Long) session.getAttribute("id");
        commentLikeService.deleteCommentLike(id,commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
