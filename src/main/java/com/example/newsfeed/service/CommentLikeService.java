package com.example.newsfeed.service;

import com.example.newsfeed.dto.like.CommentLikeRequest;

public interface CommentLikeService {
    void addCommentLike(CommentLikeRequest request);
}
