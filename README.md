# Commit Message Convention

```
타입은 태그와 제목으로 구성되고, 태그는 영어로 쓰되 첫 문자는 대문자로 한다.
태그 : 제목의 형태이며, :뒤에만 space가 있음에 유의한다.
내용은 간단하게 영어로 작성

Feat : 새로운 기능 추가 
Fix : 버그 수정 
Docs : 문서 수정 
Style : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우 
Refactor : 코드 리펙토링 
Test : 테스트 코드, 리펙토링 테스트 코드 추가 
Chore : 빌드 업무 수정, 패키지 매니저 수정
```

# ERD

```mermaid
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
```
