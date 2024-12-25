package com.example.newsfeed.service;

import com.example.newsfeed.entity.Post;
import com.example.newsfeed.entity.PostLike;
import com.example.newsfeed.repository.PostLikeRepository;
import com.example.newsfeed.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PostLikeService {

    PostLikeRepository postLikeRepository;
    PostRepository postRepository;

    public PostLikeService(PostLikeRepository postLikeRepository, PostRepository postRepository) {
        this.postLikeRepository = postLikeRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public void addPostLike(Long id, Long post) {
        Post findPost = postRepository.findById(post.getId()).orElseThrow(() -> new RuntimeException("게시글을 찾을수 없음"));
        findPost.upPostLike();
        postRepository.save(findPost);
        postLikeRepository.save(new PostLike(findPost, id));
    }

    public int getPostLike(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("게시글을 찾을수 없음"));

        return post.getPostLikeCount();
    }

    public void deletePostLike(Long userId, Long postId) {
        postLikeRepository.deleteByPostIdAndUserId(postId, userId);
    }
}
