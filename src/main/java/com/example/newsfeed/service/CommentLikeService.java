package com.example.newsfeed.service;

import com.example.newsfeed.entity.Comment;
import com.example.newsfeed.entity.CommentLike;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.repository.CommentRepository;
import com.example.newsfeed.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final UserService userService;
    private final CommentService commentService;

    @Transactional
    public void addCommentLike(String username, Long commentId) {
        Comment findComment = commentService.findById(commentId);
        User byEmail = userService.findByEmail(username);
        findComment.upLikeCount();
        commentService.save(findComment);
        commentLikeRepository.save(new CommentLike(findComment,byEmail.getId()));
    }

    public int getCommentLike(Long commentId) {
        Comment comment = commentService.findById(commentId);
        return comment.getCommentLikeCount();
    }

    public void deleteCommentLike(Long userId, Long commentId) {
        commentLikeRepository.deleteByCommentIdAndUserId(commentId, userId);
        Comment comment = commentService.findById(commentId);
        comment.downCommentLike();
        commentService.save(comment);
    }
}
