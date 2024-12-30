package com.example.newsfeed.dto.post;

import com.example.newsfeed.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateRePostResponse {

    Long postId;
    boolean isOriginal;
    int repostCount;

    public static CreateRePostResponse from(Post post) {
        return CreateRePostResponse.builder()
                .postId(post.getPostId())
                .isOriginal(post.isOriginal())
                .repostCount(post.getRepostCount())
                .build();
    }
}
