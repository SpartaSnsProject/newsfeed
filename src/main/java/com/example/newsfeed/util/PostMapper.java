package com.example.newsfeed.util;

import com.example.newsfeed.dto.post.PostResponseDto;
import com.example.newsfeed.entity.Post;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostMapper {
    public static PostResponseDto toDto(Post post){
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


