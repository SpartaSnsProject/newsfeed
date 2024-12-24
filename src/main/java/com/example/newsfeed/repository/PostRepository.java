package com.example.newsfeed.repository;

import com.example.newsfeed.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.user.displayName = :displayName")
    List<Post> findByDisplayName(String displayName);

    @Query("SELECT p FROM Post p WHERE p.user.id = :userId")
    List<Post> findByUserId(Long userId);

    boolean existsByOriginalPost_PostIdAndUser_Id(Long userId, Long originalPostId);
}
