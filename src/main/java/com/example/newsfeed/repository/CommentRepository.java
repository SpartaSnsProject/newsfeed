package com.example.newsfeed.repository;

import com.example.newsfeed.entity.Comment;
import com.example.newsfeed.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);

    List<Comment> findAllByComment(Comment comment);
}
