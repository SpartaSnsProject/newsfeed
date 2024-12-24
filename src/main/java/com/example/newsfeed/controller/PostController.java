package com.example.newsfeed.controller;

import com.example.newsfeed.dto.post.PostRequestDto;
import com.example.newsfeed.dto.post.PostResponseDto;
import com.example.newsfeed.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostResponseDto> createPost(
            @Valid @RequestBody PostRequestDto requestDto,
            @RequestHeader("Authorization") String authHeader
    ) {
        PostResponseDto responseDto = postService.createPost(requestDto, authHeader);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostResponseDto> updatePost(
            @Valid @RequestBody PostRequestDto requestDto,
            @PathVariable Long postId,
            @RequestHeader("Authorization") String authHeader
    ) {
        PostResponseDto responseDto = postService.updatePost(requestDto, postId, authHeader);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deletePst(
            @PathVariable Long postId,
            @RequestHeader("Authorization") String authHeader
    ) {
        postService.deletePost(postId, authHeader);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
