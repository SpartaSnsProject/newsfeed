package com.example.newsfeed.service;

import com.example.newsfeed.entity.Post;
import com.example.newsfeed.entity.PostLike;
import com.example.newsfeed.entity.User;
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
    private final PostService postService;
    private final UserService userService;

    @Transactional
    public void addPostLike(String username, Long postId) {

        User byEmail = userService.findByEmail(username);
        Post findPost = postService.findById(postId);

        postLikeRepository.save(new PostLike(findPost, byEmail.getId() ));

        findPost.upPostLike();

        postService.save(findPost);

    }

    public int getPostLike(Long postId) {

        Post post = postService.findById(postId);

        return post.getPostLikeCount();
    }

    @Transactional
    public void deletePostLike(String userName, Long postId) {
        Post post = postService.findById(postId);
        User byEmail = userService.findByEmail(userName);

        postLikeRepository.deleteByPost_PostIdAndUserId(postId, byEmail.getId());

        post.downPostLike();
        postService.save(post);
    }
}
