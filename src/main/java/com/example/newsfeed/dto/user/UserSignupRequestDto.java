package com.example.newsfeed.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserSignupRequestDto {
    @NotBlank(message = "사용자명은 필수입니다.")
    @Size(min = 3, max = 15, message = "사용자명은 3-15자 사이여야 합니다.")
    private String username;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^[A-Za-z\\d@$!%*#?&]{8,20}$",  // 최대 길이를 20자로 제한
            message = "비밀번호는 8-20자이며, 영문자, 숫자, 특수문자를 포함해야 합니다.")
    private String password;

    private String displayName;
    private String bio;
    private LocalDate birthDate;
}
