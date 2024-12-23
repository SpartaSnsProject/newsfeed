package com.example.newsfeed.service;


import com.example.newsfeed.dto.like.RequestComment;

public interface CommentLikeService {
    void addCommentLike(Long id, RequestComment commentId);

    void deleteCommentLike(Long id, Long commentId);

    int getCommentLike(Long commentId);
}
