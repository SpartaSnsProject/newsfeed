package com.example.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 15)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String displayName;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private LocalDate birthDate;
    private boolean protectedTweets = false;

    private int followersCount = 0;
    private int followingCount = 0;
    private int tweetsCount = 0;

    private boolean isDeleted = false;

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }


    @Builder
    private User(String username, String email, String password, String displayName,
                 String bio, LocalDate birthDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.bio = bio;
        this.birthDate = birthDate;
        this.protectedTweets = false;
        this.followersCount = 0;
        this.followingCount = 0;
        this.tweetsCount = 0;
        this.isDeleted = false;
    }
}
