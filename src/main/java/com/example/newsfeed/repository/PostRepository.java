package com.example.newsfeed.repository;

import com.example.newsfeed.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUser_DisplayName(String displayName);
    List<Post> findByUser_Id(Long userId);
    boolean existsByUser_IdAndOriginalPost_PostId(Long userId, Long originalPostId);

    Post findByUser_IdAndOriginalPost_PostId(Long userId, Long originalPostId);

}
