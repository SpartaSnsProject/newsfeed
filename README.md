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

API 명세서
1. 사용자(User) API
1.1 사용자 생성
POST /api/users
Request Body:
- Response:
201 Created: 생성된 사용자 정보
400 Bad Request: 유효하지 않은 입력
1.2 사용자 조회
GET /api/users/{id}
Response:
200 OK: 사용자 정보
404 Not Found: 사용자가 존재하지 않음
1.3 사용자 업데이트
PUT /api/users/{id}
Request Body: (업데이트할 필드만 포함)
- Response:
200 OK: 업데이트된 사용자 정보
404 Not Found: 사용자가 존재하지 않음
1.4 사용자 삭제
DELETE /api/users/{id}
Response:
204 No Content: 삭제 성공
404 Not Found: 사용자가 존재하지 않음
---
2. 게시글(Post) API
2.1 게시글 생성
POST /api/posts
Request Body:
- Response:
201 Created: 생성된 게시글 정보
400 Bad Request: 유효하지 않은 입력
2.2 게시글 조회
GET /api/posts/{id}
Response:
200 OK: 게시글 정보
404 Not Found: 게시글이 존재하지 않음
2.3 게시글 업데이트
PUT /api/posts/{id}
Request Body:
- Response:
200 OK: 업데이트된 게시글 정보
404 Not Found: 게시글이 존재하지 않음
2.4 게시글 삭제
DELETE /api/posts/{id}
Response:
204 No Content: 삭제 성공
404 Not Found: 게시글이 존재하지 않음
---
3. 댓글(Comment) API
3.1 댓글 생성
POST /api/comments
Request Body:
- Response:
201 Created: 생성된 댓글 정보
400 Bad Request: 유효하지 않은 입력
3.2 댓글 조회
GET /api/comments/{id}
Response:
200 OK: 댓글 정보
404 Not Found: 댓글이 존재하지 않음
3.3 댓글 업데이트
PUT /api/comments/{id}
Request Body:
- Response:
200 OK: 업데이트된 댓글 정보
404 Not Found: 댓글이 존재하지 않음
3.4 댓글 삭제
DELETE /api/comments/{id}
Response:
204 No Content: 삭제 성공
404 Not Found: 댓글이 존재하지 않음
---
4. 친구(Friend) API
4.1 친구 관계 생성
POST /api/friends
Request Body:
- Response:
201 Created: 생성된 친구 관계 정보
400 Bad Request: 유효하지 않은 입력
4.2 친구 관계 조회
GET /api/friends/{id}
Response:
200 OK: 친구 관계 정보
404 Not Found: 친구 관계가 존재하지 않음
4.3 친구 관계 삭제
DELETE /api/friends/{id}
Response:
204 No Content: 삭제 성공
404 Not Found: 친구 관계가 존재하지 않음
---
5. 좋아요(Like) API
5.1 게시글 좋아요 생성
POST /api/postlikes
Request Body:
- Response:
201 Created: 생성된 좋아요 정보
400 Bad Request: 유효하지 않은 입력
5.2 댓글 좋아요 생성
POST /api/commentlikes
Request Body:
- Response:
201 Created: 생성된 좋아요 정보
400 Bad Request: 유효하지 않은 입력
5.3 좋아요 삭제
DELETE /api/postlikes/{id}
Response:
204 No Content: 삭제 성공
404 Not Found: 좋아요가 존재하지 않음
5.4 댓글 좋아요 삭제
DELETE /api/commentlikes/{id}
Response:
204 No Content: 삭제 성공
404 Not Found: 좋아요가 존재하지 않음
---
    COMMENTLIKE {
        userId UNIQUE
    }
```
