package com.example.newsfeed.repository;

import com.example.newsfeed.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<CommentLike, Long> {
    void deleteByCommentIdAndUserId(Long commentId, Long userId);
}
