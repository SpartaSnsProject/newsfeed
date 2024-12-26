package com.example.newsfeed.repository;

import com.example.newsfeed.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUser_DisplayName(String displayName);
    List<Post> findByUser_Id(Long userId);
    boolean existsByUser_IdAndOriginalPost_PostId(Long userId, Long originalPostId);

    Post findByUser_IdAndOriginalPost_PostId(Long userId, Long originalPostId);

    @Query("SELECT p FROM Post p " +
            "WHERE p.user.id = :userId " +
            "OR p.user.id IN (" +
            "   SELECT f.following.id FROM Friend f WHERE f.follower.id IN :friendIds" +
            ") ")
    List<Post> findPostsByUser_Id(@Param("userId") Long userId, @Param("friendIds") List<Long> friendIds);

    List<Post> findAllByIdIn(List<Integer> list);
}
