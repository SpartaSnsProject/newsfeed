package com.example.newsfeed.dto.user;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    @Size(min = 3, max = 15, message = "사용자명은 3-15자 사이여야 합니다.")
    private String username;
    @Size(max = 50, message = "표시 이름은 50자를 넘을 수 없습니다.")
    private String displayName;

    @Size(max = 160, message = "자기소개는 160자를 넘을 수 없습니다.")
    private String bio;

    @Size(max = 50, message = "위치는 50자를 넘을 수 없습니다.")
    private String location;

    private LocalDate birthDate;
    private Boolean protectedTweets;
}
