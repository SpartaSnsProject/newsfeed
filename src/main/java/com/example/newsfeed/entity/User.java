package com.example.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "`user`")  // MySQL에서 user는 예약어이므로 백틱으로 감싸줍니다
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "display_name", unique = true, nullable = false)
    private String displayName;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "protected_tweets")
    private boolean protectedTweets = false;

    @Column(name = "followers_count")
    private int followersCount = 0;

    @Column(name = "following_count")
    private int followingCount = 0;

    @Column(name = "tweets_count")
    private int tweetsCount = 0;

    @Column(name = "is_deleted")
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
