package com.example.newsfeed.service;

import com.example.newsfeed.dto.post.PostRequestDto;
import com.example.newsfeed.dto.post.PostResponseDto;
import com.example.newsfeed.entity.Post;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.exception.ForbiddenException;
import com.example.newsfeed.exception.NotFoundException;
import com.example.newsfeed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
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
    //불필요한 메서드 제거예정(한성우)
//
//    private User getUserByEmail(String email) {
//        return userService.findByEmail(email);
//    }
//
//    private Long getUserIdByEmail(String email) {
//        return userService.findUserIdByEmail(email);
//    }

    //검증 예외객체,메시지 변경(한성우)
    private void validatePostOwnership(Post post, Long userId) {
        if (!post.getUser().getId().equals(userId)) {
            throw new ForbiddenException("게시글에 수정 권한이 없습니다.");
        }
    }

    public void save(Post post){
        postRepository.save(post);
    }

    public void delete(Post post){
        postRepository.delete(post);
    }

    public boolean isRepost(Long userId, Long originalPostId) {
        return postRepository.existsByUser_IdAndOriginalPost_PostId(userId, originalPostId);
    }

    public Post getRelatedRepost(Long userId, Long originalPostId) {
        return postRepository.findByUser_IdAndOriginalPost_PostId(userId, originalPostId);
    }

    @Transactional(readOnly = true)
    //메서드 이름변경 findByDisplayName -> findAllByDisplayName (한성우)0
    public List<PostResponseDto> findAllByDisplayName(String displayName) {
        List<Post> posts = postRepository.findAllByUser_DisplayName(displayName);
        return posts.stream()
                .map(PostResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllPosts(String email) {
        Long userId = userService.findUserIdByEmail(email);
        List<Post> posts = postRepository.findByUser_Id(userId);
        return posts.stream()
                .map(PostResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PostResponseDto findByPostId(Long postId) {
        Post post = findById(postId);
        return PostResponseDto.from(post);
    }

    //메서드명(기존이름) getPostById 직관적이지 않음 변경
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
            if (!(i ==byEmail.getId())) {
                numbers.add(i);
            }
        }
        Collections.shuffle(numbers);
        List<Long> list = numbers.subList(0, 10);
        return postRepository.findAllByPostIdIn(list).stream().map(PostResponseDto::from).toList();
    }
}