package com.example.newsfeed.repository;

import com.example.newsfeed.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    void deleteByCommentIdAndUserId(Long commentId, Long userId);

    List<CommentLike> findByCommentId(Long commentId);
}
