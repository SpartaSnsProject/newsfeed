package com.example.newsfeed.dto.user;

import com.example.newsfeed.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileResponseDto {
    private Long id;
    private String username;
    private String displayName;
    private String bio;

    private int followersCount;
    private int followingCount;
    private int tweetsCount;
    private LocalDateTime createdAt;

    @Builder
    private UserProfileResponseDto(Long id, String username, String displayName, String bio,
                                    int followersCount, int followingCount, int tweetsCount, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.bio = bio;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
        this.tweetsCount = tweetsCount;
        this.createdAt = createdAt;
    }

    public static UserProfileResponseDto from(User user) {
        return UserProfileResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .displayName(user.getDisplayName())
                .bio(user.getBio())
                .followersCount(user.getFollowersCount())
                .followingCount(user.getFollowingCount())
                .tweetsCount(user.getTweetsCount())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
