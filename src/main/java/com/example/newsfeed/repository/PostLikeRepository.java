package com.example.newsfeed.repository;

import com.example.newsfeed.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
    List<PostLike> findByPostId(Long postId);

    void deleteByPostIdAndUserId(Long postId, Long userId);
}
