package com.example.newsfeed.util;

import com.example.newsfeed.dto.post.RepostResponseDto;
import com.example.newsfeed.entity.Post;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RepostMapper {
    public static RepostResponseDto toDto(Post post, Post originalPost) {
        return new RepostResponseDto(
                post.getPostId(),
                post.getUser().getUsername(),
                post.getUser().getDisplayName(),
                post.getContent(),
                post.getCreatedAt(),
                post.getModifiedAt(),
                originalPost.getPostId(),
                originalPost.getUser().getUsername(),
                originalPost.getUser().getDisplayName(),
                originalPost.getContent(),
                originalPost.getCreatedAt(),
                originalPost.getModifiedAt()
        );
    }
}
