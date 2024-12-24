package com.example.newsfeed.controller;


import com.example.newsfeed.dto.comment.RequestPatchComment;
import com.example.newsfeed.dto.comment.ResponseComment;
import com.example.newsfeed.dto.like.RequestComment;
import com.example.newsfeed.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class CommentController {

    CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<ResponseComment> addComment(@RequestBody RequestComment requestComment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        ResponseComment responseComment = commentService.addComment(requestComment, username);

        return new ResponseEntity<>(responseComment, HttpStatus.CREATED);
    }

    @GetMapping("/post/{post_id}")
    public ResponseEntity<Map<String, List<ResponseComment>>> getCommentByPost(@PathVariable("post_id") Long postId) {
        List<ResponseComment> commentByPost = commentService.getCommentByPost(postId);
        Map<String, List<ResponseComment>> response = new HashMap<>();
        response.put("comment_by_post", commentByPost);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/comment/{comment_id}")
    public ResponseEntity<Map<String, List<ResponseComment>>> getCommentByComment(@PathVariable("comment_id") Long commentId) {
        List<ResponseComment> commentByComment = commentService.getCommentByComment(commentId);
        Map<String, List<ResponseComment>> response = new HashMap<>();
        response.put("comment_by_comment", commentByComment);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<ResponseComment> updateComment(@RequestBody RequestPatchComment comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        ResponseComment responseComment = commentService.updateComment(comment, username);
        return new ResponseEntity<>(responseComment, HttpStatus.OK);
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<ResponseComment> deleteComment(@PathVariable("comment_id")Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        ResponseComment responseComment = commentService.deleteComment(commentId, username);
       return new ResponseEntity<>(responseComment, HttpStatus.NO_CONTENT);
    }
}
