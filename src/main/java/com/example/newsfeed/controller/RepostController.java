package com.example.newsfeed.controller;

import com.example.newsfeed.dto.post.PostRequestDto;
import com.example.newsfeed.dto.post.RepostResponseDto;
import com.example.newsfeed.service.RepostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Repost", description = "리포스트 관련 API")
public class RepostController {

    private final RepostService repostService;

    @PostMapping("/{originalPostId}")
    @Operation(
            summary = "리포스트 생성",
            description = "원본 포스트의 고유식별자로 로그인한 유저의 리포스트를 생성합니다.",
            security = {@SecurityRequirement(name = "Bearer Authentication")}
    )
    public ResponseEntity<RepostResponseDto> createRepost(
            @RequestBody PostRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long originalPostId
    ) {
        String email = userDetails.getUsername();
        RepostResponseDto responseDto = repostService.toggleRepost(requestDto, email, originalPostId);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/reposts/{repostId}")
    @Operation(
            summary = "리포스트 수정",
            description = "리포스트의 고유 식별자로 리포스트를 수정합니다.",
            security = {@SecurityRequirement(name = "Bearer Authentication")}
    )
    public ResponseEntity<RepostResponseDto> updateRepost(
            @RequestBody PostRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long repostId
    ) {
        String email = userDetails.getUsername();
        RepostResponseDto responseDto = repostService.updateRepost(repostId, requestDto, email);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/reposts/{repostId}")
    @Operation(
            summary = "포스트 삭제",
            description = "리포스트의 고유식별자로 리포스트를 삭제합니다.",
            security = {@SecurityRequirement(name = "Bearer Authentication")}
    )
    public ResponseEntity<Void> deleteRepost(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long repostId
    ) {
        String email = userDetails.getUsername();
        repostService.deleteRepost(repostId, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}