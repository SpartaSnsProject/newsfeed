package com.example.newsfeed.dto.post;

import com.example.newsfeed.entity.Post;
import com.example.newsfeed.entity.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;


@Getter
public class PostRequestDto {

    @NotBlank(message = "포스트 공백, null 불가")
    @Size(max = 140, message = "포스팅 140자 제한")
    private String content;

    public Post toEntity(User user){
        return new Post(user, content);
    }
}
