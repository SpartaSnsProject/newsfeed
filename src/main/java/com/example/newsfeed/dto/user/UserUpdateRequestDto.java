package com.example.newsfeed.dto.user;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    @Size(min = 3, max = 15, message = "사용자명은 3-15자 사이여야 합니다.")
    private String username;
    private String bio;
    private Boolean protectedTweets;
}