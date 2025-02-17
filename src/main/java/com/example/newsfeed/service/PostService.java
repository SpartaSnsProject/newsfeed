package com.example.newsfeed.service;

import com.example.newsfeed.dto.post.PostRequestDto;
import com.example.newsfeed.dto.post.PostResponseDto;
import com.example.newsfeed.entity.Post;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.exception.ForbiddenException;
import com.example.newsfeed.exception.NotFoundException;
import com.example.newsfeed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final FriendService friendService;

    public PostResponseDto createPost(PostRequestDto requestDto, String email) {
        User user = userService.findByEmail(email);
        Post post = requestDto.toEntity(user);

        postRepository.save(post);

        return PostResponseDto.from(post);
    }

    public PostResponseDto updatePost(PostRequestDto requestDto, Long postId, String email) {
        Long userId = userService.findUserIdByEmail(email);
        Post post = findById(postId);
        validatePostOwnership(post, userId);

        post.updatePost(requestDto.getContent());

        return PostResponseDto.from(post);
    }

    public void deletePost(Long postId, String email) {
        Long userId = userService.findUserIdByEmail(email);
        Post post = findById(postId);
        validatePostOwnership(post, userId);

        postRepository.delete(post);
    }

    private void validatePostOwnership(Post post, Long userId) {
        if (!post.getUser().getId().equals(userId)) {
            throw new ForbiddenException("게시글 수정/삭제 권한이 없습니다.");
        }
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

    public boolean isRepost(Long userId, Long originalPostId) {
        return postRepository.existsByUser_IdAndOriginalPost_PostId(userId, originalPostId);
    }

    public Post getRelatedRepost(Long userId, Long originalPostId) {
        return postRepository.findByUser_IdAndOriginalPost_PostId(userId, originalPostId);
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAllByDisplayName(String displayName, Pageable pageable) {
        Page<Post> posts = postRepository.findAllByUser_DisplayName(displayName, pageable);
        return posts.map(PostResponseDto::from);
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAllPosts(String email, Pageable pageable) {
        Long userId = userService.findUserIdByEmail(email);

        Page<Post> posts = postRepository.findByUser_Id(userId, pageable);
        return posts.map(PostResponseDto::from);
    }

    @Transactional(readOnly = true)
    public PostResponseDto findByPostId(Long postId) {
        Post post = findById(postId);
        return PostResponseDto.from(post);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시글 입니다."));
    }

    public List<PostResponseDto> postSuggestion(String username) {
        User byEmail = userService.findByEmail(username);
        List<Long> numbers = new ArrayList<>();
        List<Post> all = postRepository.findAll();

        int size = all.size();

        for (long i = 1; i < size; i++) {
            if (!(i == byEmail.getId())) {
                numbers.add(i);
            }
        }
        if (numbers.size() < 10) {
            throw new NotFoundException("서버의 게시글이 10개 미만입니다.");
        }

        Collections.shuffle(numbers);
        List<Long> list = numbers.subList(0, 10);

        return postRepository.findAllByPostIdIn(list).stream().map(PostResponseDto::from).toList();
    }

    public Page<PostResponseDto> findAll(Pageable pageable) {
        Page<Post> all = postRepository.findAll(pageable);
        return all.map(PostResponseDto::from);
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> findTimeline(String email, Pageable pageable) {
        User user = userService.findByEmail(email);
        Long userId = user.getId();

        List<Long> friendIds = friendService.findFollowingIds(user);
        Page<Post> posts = postRepository.findUserAndFollowingPosts(userId, friendIds, pageable);

        return posts.map(PostResponseDto::from);
    }
}