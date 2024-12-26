package com.example.newsfeed.dto.user;

import com.example.newsfeed.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponseDto {
    private Long id;
    private String email;
    private String displayName;
    private String token;    // JWT 토큰

    public static UserLoginResponseDto of(User user, String token) {
        return UserLoginResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .displayName(user.getDisplayName())
                .token(token)
                .build();
    }
}