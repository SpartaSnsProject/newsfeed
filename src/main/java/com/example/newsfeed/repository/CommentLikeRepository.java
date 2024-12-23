package com.example.newsfeed.repository;

import com.example.newsfeed.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    void deleteByCommentIdAndUserId(Long commentId, Long userId);

    List<CommentLike> findByCommentId(Long commentId);
}
