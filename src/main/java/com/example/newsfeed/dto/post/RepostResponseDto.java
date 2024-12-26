package com.example.newsfeed.dto.post;

import com.example.newsfeed.entity.Post;
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

    public static RepostResponseDto from(Post post, Post originalPost){
        if (post == null) {
            return new RepostResponseDto(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    originalPost.getUser().getId(),
                    originalPost.getUser().getUsername(),
                    originalPost.getUser().getDisplayName(),
                    originalPost.getContent(),
                    originalPost.getRepostCount(),
                    originalPost.getCreatedAt(),
                    originalPost.getModifiedAt()
            );
        }

        return RepostResponseDto.builder()
                .rePostId(post.getPostId())
                .username(post.getUser().getUsername())
                .displayName(post.getUser().getDisplayName())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .originalPostId(originalPost.getPostId())
                .originalPostUsername(originalPost.getUser().getUsername())
                .originalDisplayName(originalPost.getUser().getDisplayName())
                .originalPostContent(originalPost.getContent())
                .repostCount(originalPost.getRepostCount())
                .originalCreatedAt(originalPost.getCreatedAt())
                .originalModifiedAt(originalPost.getModifiedAt())
                .build();
    }
}