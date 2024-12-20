erDiagram
    USER {
        Long id PK
        String username
        String email
        String password
        String displayName
        String bio
        String location
        String profileImageUrl
        String bannerImageUrl
        LocalDate birthDate
        boolean protectedTweets
        int followersCount
        int followingCount
        int tweetsCount
    }
    POST {
        Long id PK
        String content
        boolean deleted
    }
    COMMENT {
        Long id PK
        Long replyCount
        Long postId FK
        String userName
    }
    FRIEND {
        Long id PK
        Long userId1 FK
        Long userId2 FK
    }
    POSTLIKE {
        Long id PK
        Long postId FK
        Long userId
    }
    COMMENTLIKE {
        Long id PK
        Long commentId FK
        Long userId
    }
    USER ||--o{ POST : "writes"
    POST ||--o{ COMMENT : "has"
    POST ||--o{ POSTLIKE : "receives"
    COMMENT ||--o{ COMMENTLIKE : "receives"
    FRIEND ||--o{ USER : "friends with"
    %% 유니크 제약 조건
    POSTLIKE {
        userId UNIQUE
    }
    COMMENTLIKE {
        userId UNIQUE
    }
