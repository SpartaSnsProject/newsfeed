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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Tag(name = "Post", description = "포스트 관련 API")
public class PostController {

    private final PostService postService;

    @Operation(
            summary = "포스트 생성",
            description = "로그인한 유저의 포스트를 생성합니다.",
            security = {@SecurityRequirement(name = "Bearer Authentication")}
    )
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostResponseDto> createPost(
            @Valid @RequestBody PostRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails.getUsername();
        PostResponseDto responseDto = postService.createPost(requestDto, email);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(
            summary = "포스트 단건 조회",
            description = "포스트의 고유 식별자로 포스트를 조회합니다.",
            security = {@SecurityRequirement(name = "Bearer Authentication")}
    )
    @GetMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostResponseDto> findByPostId(
            @PathVariable Long postId
    ) {
        PostResponseDto responseDto = postService.findByPostId(postId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(
            summary = "포스트 목록 조회",
            description = "유저 핸들아이디로 포스트 목록를 조회합니다.",
            security = {@SecurityRequirement(name = "Bearer Authentication")}
    )
    @GetMapping("/display/{displayName}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostListResponseDto> findByDisplayId(
            @PathVariable String displayName
    ) {
        PostListResponseDto postListResponseDto = new PostListResponseDto(postService.findByDisplayName(displayName));
        return new ResponseEntity<>(postListResponseDto, HttpStatus.OK);
    }

    @Operation(
            summary = "포스트 목록 조회",
            description = "로그인한 유저의 포스트 목록을 조회합니다.",
            security = {@SecurityRequirement(name = "Bearer Authentication")}
    )
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostListResponseDto> findAllPosts(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails.getUsername();
        PostListResponseDto postListResponseDto = new PostListResponseDto(postService.findAllPosts(email));
        return new ResponseEntity<>(postListResponseDto, HttpStatus.OK);
    }

    @Operation(
            summary = "포스트 수정",
            description = "포스트 고유 식별자로 포스트를 수정합니다.",
            security = {@SecurityRequirement(name = "Bearer Authentication")}
    )
    @PutMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostResponseDto> updatePost(
            @Valid @RequestBody PostRequestDto requestDto,
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails.getUsername();
        PostResponseDto responseDto = postService.updatePost(requestDto, postId, email);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(
            summary = "포스트 삭제",
            description = "포스트의 고유 식별자로 포스트를 삭제합니다.",
            security = {@SecurityRequirement(name = "Bearer Authentication")}
    )
    @DeleteMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deletePst(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails.getUsername();
        postService.deletePost(postId, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
