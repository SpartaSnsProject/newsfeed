package com.example.newsfeed.service;

import com.example.newsfeed.dto.post.PostRequestDto;
import com.example.newsfeed.dto.post.PostResponseDto;
import com.example.newsfeed.entity.Post;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.exception.post.PostNotFoundException;
import com.example.newsfeed.exception.user.UnauthorizedException;
import com.example.newsfeed.repository.PostRepository;
import com.example.newsfeed.security.JwtUtil;
import com.example.newsfeed.util.PostMapper;
import com.example.newsfeed.util.postCont.PostMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public PostResponseDto createPost(PostRequestDto requestDto, String authHeader) {
        String username = jwtUtil.extractUsername(authHeader);

        User user = getUserById(username);
        Post post = requestDto.toEntity(user);
        postRepository.save(post);
        return PostMapper.toDto(post);
    }

    public PostResponseDto findByPostId(Long postId) {
        Post post = getPostById(postId);
        return PostMapper.toDto(post);
    }

    public List<PostResponseDto> findByDisplayName(String displayName) {
        List<Post> posts = postRepository.findByDisplayName(displayName);

        return posts.stream()
                .map(PostMapper::toDto)
                .toList();
    }


    public List<PostResponseDto> findAllPosts(String authHeader) {
        String username = jwtUtil.extractUsername(authHeader);
        Long userId = userService.findUserIdByUsername(username);

        List<Post> posts = postRepository.findByUserId(userId);

        return posts.stream()
                .map(PostMapper::toDto)
                .toList();
    }

    public PostResponseDto updatePost(PostRequestDto requestDto, Long postId, String authHeader) {
        String username = jwtUtil.extractUsername(authHeader);
        Long userId = userService.findUserIdByUsername(username);

        Post post = getPostById(postId);
        validatePostOwnership(post, userId);
        post.updatePost(requestDto.getContent());
        return PostMapper.toDto(post);
    }

    public void deletePost(Long postId, String authHeader) {
        String username = jwtUtil.extractUsername(authHeader);
        Long userId = userService.findUserIdByUsername(username);

        Post post = getPostById(postId);
        validatePostOwnership(post, userId);
        postRepository.delete(post);
    }

    private User getUserById(String username) {
        return userService.findByUsername(username);
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(PostMessages.POST_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    private void validatePostOwnership(Post post, Long userId) {
        if (!post.getUser().getId().equals(userId)) {
            throw new UnauthorizedException(PostMessages.UN_AUTH_UPDATE);
        }
    }

    public void save(Post post){
        postRepository.save(post);
    }

    public void delete(Post post){
        postRepository.delete(post);
    }
}
