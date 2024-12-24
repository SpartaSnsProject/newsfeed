package com.example.newsfeed.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RepostResponseDto {

    private String userName;
    private String displayName;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long originalPostId;
    private String originalPostUserName;
    private String originalDisplayName;
    private String originalPostContent;
    private LocalDateTime originalCreatedAt;
    private LocalDateTime originalModifiedAt;
}
