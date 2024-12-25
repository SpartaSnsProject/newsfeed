package com.example.newsfeed.service;

import com.example.newsfeed.entity.Comment;
import com.example.newsfeed.entity.CommentLike;
import com.example.newsfeed.repository.CommentRepository;
import com.example.newsfeed.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void addCommentLike(Long id, Long commentId) {
        Comment findComment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("게시물을 찾을수없음"));
        findComment.upLikeCount();
        commentRepository.save(findComment);
        commentLikeRepository.save(new CommentLike(findComment,id));
    }

    public int getCommentLike(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("게시물 못찾음 익셉션"));
        return comment.getCommentLikeCount();
    }

    public void deleteCommentLike(Long userId, Long commentId) {
        commentLikeRepository.deleteByCommentIdAndUserId(commentId, userId);
    }
}
