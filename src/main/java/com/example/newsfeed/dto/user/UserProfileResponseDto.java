package com.example.newsfeed.dto.user;

import com.example.newsfeed.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UserProfileResponseDto {
    private Long id;
    private String username;
    private String displayName;
    private String bio;
    private LocalDate birthDate;
    private int followersCount;
    private int followingCount;
    private int tweetsCount;

    public static UserProfileResponseDto from(User user) {
        return UserProfileResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .displayName(user.getDisplayName())
                .bio(user.getBio())
                .birthDate(user.getBirthDate())
                .followersCount(user.getFollowersCount())
                .followingCount(user.getFollowingCount())
                .tweetsCount(user.getTweetsCount())
                .build();
    }
}