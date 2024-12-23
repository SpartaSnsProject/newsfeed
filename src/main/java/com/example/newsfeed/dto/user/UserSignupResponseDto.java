package com.example.newsfeed.dto.user;

import com.example.newsfeed.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignupResponseDto {
    private Long id;
    private String username;
    private String displayName;
    private String message;

    public static UserSignupResponseDto from(User user) {
        return UserSignupResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .displayName(user.getDisplayName())
                .message("회원가입이 성공적으로 완료되었습니다.")
                .build();
    }
}