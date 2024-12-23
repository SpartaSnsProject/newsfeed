package com.example.newsfeed.dto.user;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UserUpdateRequestDto {
    @Size(max = 50, message = "표시 이름은 50자를 넘을 수 없습니다.")
    private String displayName;

    @Size(max = 160, message = "자기소개는 160자를 넘을 수 없습니다.")
    private String bio;

    @Size(max = 50, message = "위치는 50자를 넘을 수 없습니다.")
    private String location;

    private LocalDate birthDate;
    private Boolean protectedTweets;
}
