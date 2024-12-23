package com.example.newsfeed.controller;

import com.example.newsfeed.dto.like.CommentLikeRequest;
import com.example.newsfeed.service.CommentLikeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commentlikes")
public class CommentLikeController {

    private Long hi;
    private final CommentLikeService commentLikeService;

    public CommentLikeController(CommentLikeService commentLikeService) {
        this.commentLikeService = commentLikeService;
    }

    @PostMapping
    public ResponseEntity<Void> addCommentLike(@RequestBody CommentLikeRequest request, HttpSession session) {
        Long id = (Long) session.getAttribute("id");
        commentLikeService.addCommentLike(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentLike(@PathVariable String id) {

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
