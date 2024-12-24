package com.example.newsfeed.controller;

import com.example.newsfeed.dto.post.PostListResponseDto;
import com.example.newsfeed.dto.post.PostRequestDto;
import com.example.newsfeed.dto.post.PostResponseDto;
import com.example.newsfeed.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Tag(name = "Post", description = "포스트 관련 API")
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

    @Operation(
            summary = "포스트 조회(by postId)",
            description = "사용자 ID로 프로필을 조회합니다.",
            security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    @GetMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostResponseDto> findByPostId(
            @PathVariable Long postId
    ) {
        PostResponseDto responseDto = postService.findByPostId(postId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

   @GetMapping("/display/{displayName}")
   @PreAuthorize("isAuthenticated()")
   public ResponseEntity<PostListResponseDto> findByDisplayId(
           @PathVariable String displayName
   ) {
        PostListResponseDto postListResponseDto = new PostListResponseDto(postService.findByDisplayName(displayName));
        return new ResponseEntity<>(postListResponseDto, HttpStatus.OK);
   }

   @GetMapping
   @PreAuthorize("isAuthenticated()")
   public ResponseEntity<PostListResponseDto> findAllPosts(
           @RequestHeader("Authorization") String authHeader
   ) {
        PostListResponseDto postListResponseDto = new PostListResponseDto(postService.findAllPosts(authHeader));
        return new ResponseEntity<>(postListResponseDto, HttpStatus.OK);
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
