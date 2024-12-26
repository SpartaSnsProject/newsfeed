package com.example.newsfeed.service;

import com.example.newsfeed.entity.Post;
import com.example.newsfeed.entity.PostLike;
import com.example.newsfeed.repository.PostLikeRepository;
import com.example.newsfeed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    @Transactional
    public void addPostLike(Long id, Long postId) {
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("게시글을 찾을수 없음"));
        findPost.upPostLike();
        postRepository.save(findPost);
        postLikeRepository.save(new PostLike(findPost, id));
    }

    public int getPostLike(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("게시글을 찾을수 없음"));

        return post.getPostLikeCount();
    }

    public void deletePostLike(Long userId, Long postId) {
        postLikeRepository.deleteByPost_PostIdAndUserId(postId, userId);
    }
}
