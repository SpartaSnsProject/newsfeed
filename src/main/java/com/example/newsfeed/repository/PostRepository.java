package com.example.newsfeed.repository;

import com.example.newsfeed.entity.Post;
import com.example.newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser_DisplayName(String displayName);
    List<Post> findByUser_Id(Long userId);
    boolean existsByUser_IdAndOriginalPost_PostId(Long userId, Long originalPostId);

    Post findByUser_IdAndOriginalPost_PostId(Long userId, Long originalPostId);

}
