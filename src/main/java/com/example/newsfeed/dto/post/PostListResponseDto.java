package com.example.newsfeed.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostListResponseDto {

    private List<PostResponseDto> posts;
}
