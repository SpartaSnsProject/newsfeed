package com.example.newsfeed.service;

import com.example.newsfeed.dto.like.RequestPost;

public interface PostLikeService {


    void addPostLike(Long id, RequestPost post);

    int getPostLike(Long commentId);

    void deletePostLike(Long id, Long postId);
}
