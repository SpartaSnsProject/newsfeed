package com.example.newsfeed.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String displayName;
    private String bio;
    private Boolean protectedTweets;
}
