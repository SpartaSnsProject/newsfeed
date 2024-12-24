package com.example.newsfeed.controller;

import com.example.newsfeed.dto.post.PostRequestDto;
import com.example.newsfeed.dto.post.RepostResponseDto;
import com.example.newsfeed.service.RepostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class RepostController {

    private final RepostService repostService;

    @PostMapping("/{originalPostId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RepostResponseDto> createRepost(
            @RequestBody PostRequestDto requestDto,
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long originalPostId
    ) {
        RepostResponseDto responseDto = repostService.toggleRepost(requestDto, authHeader, originalPostId);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/reposts/{repostId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RepostResponseDto> updateRepost(
            @RequestBody PostRequestDto requestDto,
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long repostId
    ) {
        RepostResponseDto responseDto = repostService.updateRepost(repostId, requestDto, authHeader);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/reposts/{repostId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteRepost(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long repostId
    ) {
        repostService.deleteRepost(repostId, authHeader);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
