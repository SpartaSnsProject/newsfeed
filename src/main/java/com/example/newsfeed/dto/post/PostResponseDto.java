package com.example.newsfeed.dto.post;

import com.example.newsfeed.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class PostResponseDto {

    private Long postId;
    private String displayName;
    private String username;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private boolean isOriginal;
    private int repostCount;

    public static PostResponseDto from(Post post){
        return PostResponseDto.builder()
                .postId(post.getPostId())
                .displayName(post.getUser().getDisplayName())
                .username(post.getUser().getUsername())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .isOriginal(post.isOriginal())
                .repostCount(post.getRepostCount())
                .build();
    }
}