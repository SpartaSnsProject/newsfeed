package com.example.newsfeed.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordChangeRequestDto {
    @NotBlank(message = "현재 비밀번호는 필수입니다.")
    private String currentPassword;

    @NotBlank(message = "새 비밀번호는 필수입니다.")
    @Pattern(regexp = "^[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "비밀번호는 8-20자이며, 영문자, 숫자, 특수문자를 포함해야 합니다.")
    private String newPassword;

    @NotBlank(message = "새 비밀번호 확인은 필수입니다.")
    private String confirmPassword;
}
