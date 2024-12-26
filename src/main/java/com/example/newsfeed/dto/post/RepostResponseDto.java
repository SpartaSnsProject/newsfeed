package com.example.newsfeed.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class RepostResponseDto {

    private Long rePostId;
    private String username;
    private String displayName;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long originalPostId;
    private String originalPostUsername;
    private String originalDisplayName;
    private String originalPostContent;
    private int repostCount;
    private LocalDateTime originalCreatedAt;
    private LocalDateTime originalModifiedAt;
}