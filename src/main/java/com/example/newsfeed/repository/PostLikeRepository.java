package com.example.newsfeed.repository;

import com.example.newsfeed.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
    List<PostLike> findByPostId(Long postId);

    void deleteByPostIdAndUserId(Long postId, Long userId);
}
