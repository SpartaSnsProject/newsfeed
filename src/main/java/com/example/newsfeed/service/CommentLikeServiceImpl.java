package com.example.newsfeed.service;

import com.example.newsfeed.dto.like.RequestComment;
import com.example.newsfeed.entity.Comment;
import com.example.newsfeed.entity.CommentLike;
import com.example.newsfeed.repository.CommentRepository;
import com.example.newsfeed.repository.CommentLikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentLikeServiceImpl implements CommentLikeService {
    CommentLikeRepository commentLikeRepository;
    CommentRepository commentRepository;

    public CommentLikeServiceImpl(CommentLikeRepository likeRepository) {
        this.commentLikeRepository = likeRepository;
    }


    @Override
    public void addCommentLike(Long id, RequestComment comment) {
        Comment findComment = commentRepository.findById(comment.getId()).orElseThrow(() -> new RuntimeException("게시물을 찾을수없음"));
        commentLikeRepository.save(new CommentLike(findComment,id));
    }

    @Override
    public int getCommentLike(Long commentId) {
        List<CommentLike> byCommentId = commentLikeRepository.findByCommentId(commentId);
        return byCommentId.size();
    }

    @Override
    public void deleteCommentLike(Long userId, Long commentId) {
        commentLikeRepository.deleteByCommentIdAndUserId(commentId, userId);
    }
}