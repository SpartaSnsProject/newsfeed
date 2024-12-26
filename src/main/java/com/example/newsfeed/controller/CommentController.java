package com.example.newsfeed.controller;


import com.example.newsfeed.dto.comment.RequestPatchComment;
import com.example.newsfeed.dto.comment.ResponseComment;
import com.example.newsfeed.dto.comment.RequestComment;
import com.example.newsfeed.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {

    CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "댓글추가", description = "새로운 댓글을 추가합니다.")
    @PostMapping
        public ResponseEntity<ResponseComment> addComment(@Valid @RequestBody RequestComment requestComment) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            ResponseComment responseComment = commentService.addComment(requestComment, username);

            return new ResponseEntity<>(responseComment, HttpStatus.CREATED);
    }

    @Operation(summary = "게시물의 댓글조회",description = "게시물에 있는 댓글을 조회합니다.")
    @GetMapping("/post/{post_id}")
    public ResponseEntity<Map<String, List<ResponseComment>>> getCommentByPost(@PathVariable("post_id") Long postId) {
        List<ResponseComment> commentByPost = commentService.getCommentByPost(postId);
        Map<String, List<ResponseComment>> response = new HashMap<>();
        response.put("comment_by_post", commentByPost);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "댓글의 댓글조회", description = "댓글에 있는 댓글을 조회합니다.")
    @GetMapping("/comment/{comment_id}")
    public ResponseEntity<Map<String, List<ResponseComment>>> getCommentByComment(@PathVariable("comment_id") Long commentId) {
        List<ResponseComment> commentByComment = commentService.getCommentByComment(commentId);
        Map<String, List<ResponseComment>> response = new HashMap<>();
        response.put("comment_by_comment", commentByComment);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    @PatchMapping
    public ResponseEntity<ResponseComment> updateComment(@Valid @RequestBody RequestPatchComment comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        ResponseComment responseComment = commentService.updateComment(comment, username);
        return new ResponseEntity<>(responseComment, HttpStatus.OK);
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @DeleteMapping("/{comment_id}")
    public ResponseEntity<ResponseComment> deleteComment(@PathVariable("comment_id")Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        ResponseComment responseComment = commentService.deleteComment(commentId, username);
       return new ResponseEntity<>(responseComment, HttpStatus.NO_CONTENT);
    }
}
