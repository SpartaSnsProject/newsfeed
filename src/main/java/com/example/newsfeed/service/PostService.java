package com.example.newsfeed.service;

import com.example.newsfeed.dto.post.PostRequestDto;
import com.example.newsfeed.dto.post.PostResponseDto;
import com.example.newsfeed.entity.Post;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.exception.user.UserNotFoundException;
import com.example.newsfeed.repository.PostRepository;
import com.example.newsfeed.security.JwtUtil;
import com.example.newsfeed.util.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private User getUserById(String username) {
        return userService.findByUsername(username);
    }
}
