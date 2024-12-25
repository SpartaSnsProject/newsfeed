package com.example.newsfeed.util;

import com.example.newsfeed.dto.post.RepostResponseDto;
import com.example.newsfeed.entity.Post;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RepostMapper {
    public static RepostResponseDto toDto(Post post, Post originalPost){
        return RepostResponseDto.builder()
                .rePostId(post.getPostId())
                .username(post.getUser().getUsername())
                .displayName(post.getUser().getDisplayName())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .originalPostId(originalPost.getPostId())
                .originalPostUserName(originalPost.getUser().getUsername())
                .originalDisplayName(originalPost.getUser().getDisplayName())
                .originalPostContent(originalPost.getContent())
                .repostCount(originalPost.getRepostCount())
                .originalCreatedAt(originalPost.getCreatedAt())
                .originalModifiedAt(originalPost.getModifiedAt())
                .build();
    }
}
