package com.example.newsfeed.service;

import com.example.newsfeed.dto.like.RequestComment;
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
    public void addCommentLike(Long id, RequestComment comment) {
        Comment findComment = commentRepository.findById(comment.getId()).orElseThrow(() -> new RuntimeException("게시물을 찾을수없음"));
        likeRepository.save(new CommentLike(findComment,id));
    }

    @Override
    public void deleteCommentLike(Long userId, Long commentId) {
        likeRepository.deleteByCommentIdAndUserId(commentId, userId);
    }
}
