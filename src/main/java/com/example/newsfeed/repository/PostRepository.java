package com.example.newsfeed.repository;

import com.example.newsfeed.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByUser_DisplayName(String displayName, Pageable pageable);
    Page<Post> findByUser_Id(Long userId, Pageable pageable);
    boolean existsByUser_IdAndOriginalPost_PostId(Long userId, Long originalPostId);

    Post findByUser_IdAndOriginalPost_PostId(Long userId, Long originalPostId);

    @Query("SELECT p FROM Post p " +
            "WHERE p.user.id = :userId " +
            "OR p.user.id IN :friendIds")
    Page<Post> findUserAndFollowingPosts(@Param("userId") Long userId, @Param("friendIds") List<Long> friendIds, Pageable pageable);

    List<Post> findAllByPostIdIn(List<Long> list);
}
