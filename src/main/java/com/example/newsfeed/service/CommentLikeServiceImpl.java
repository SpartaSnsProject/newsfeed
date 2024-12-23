package com.example.newsfeed.service;

import com.example.newsfeed.dto.like.CommentLikeRequest;
import com.example.newsfeed.entity.Comment;
import com.example.newsfeed.entity.CommentLike;
import com.example.newsfeed.repository.CommentRepository;
import com.example.newsfeed.repository.LikeRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeServiceImpl implements CommentLikeService {
    LikeRepository likeRepository;
    CommentRepository commentRepository;
    public CommentLikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }


    @Override
    public void addCommentLike(CommentLikeRequest request) {
        Comment comment = commentRepository.findById(request.getCommentId()).orElseThrow(() -> new RuntimeException("못찾음"));
        likeRepository.save(new CommentLike(comment));
    }
}
