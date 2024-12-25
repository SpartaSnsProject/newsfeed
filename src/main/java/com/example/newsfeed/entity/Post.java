package com.example.newsfeed.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Post extends BaseEntity{
    // 자기 자신의 객체 참조하는 방법을 택한 이유 : 모든 리포스트는 원본 포스트랑만 연결됨(X 방식) 즉 리포스트간의 관계 추적 불필요
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post originalPost;

    private String content;

    private boolean isOriginal;

    public Post(User user, String content){
        this.user = user;
        this.content = content;
        this.isOriginal = true;
    }

    public Post(User user, String content, Post originalPost) {
        this.user = user;
        this.content = content;
        this.originalPost = originalPost;
        this.isOriginal = false;
    }

    public void updatePost(String content) {
        this.content = content;
    }

    public Post repost(User user, String content) {
        return new Post(user, content, this);
    }

    public boolean isRepost() {
        return originalPost != null;
    }
}

