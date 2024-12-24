package com.example.newsfeed.service;

import com.example.newsfeed.dto.like.RequestPost;
import com.example.newsfeed.entity.Post;
import com.example.newsfeed.entity.PostLike;
import com.example.newsfeed.repository.PostLikeRepository;
import com.example.newsfeed.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostLikeServiceImpl implements PostLikeService{

    PostLikeRepository postLikeRepository;
    PostRepository postRepository;

    public PostLikeServiceImpl(PostLikeRepository postLikeRepository, PostRepository postRepository) {
        this.postLikeRepository = postLikeRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void addPostLike(Long id, RequestPost post) {
        Post findPost = postRepository.findById(post.getId()).orElseThrow(() -> new RuntimeException("게시글을 찾을수 없음"));
        postLikeRepository.save(new PostLike(findPost, id));
    }

    @Override
    public int getPostLike(Long postId) {
        List<PostLike> byPostId = postLikeRepository.findByPostId(postId);
        return byPostId.size();
    }

    @Override
    public void deletePostLike(Long userId, Long postId) {
        postLikeRepository.deleteByPostIdAndUserId(postId, userId);
    }
}
