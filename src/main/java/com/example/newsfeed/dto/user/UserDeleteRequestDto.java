package com.example.newsfeed.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDeleteRequestDto {
        @NotBlank(message = "비밀번호는 필수입니다.")
        private String password;
}