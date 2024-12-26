package com.example.newsfeed.service;

import com.example.newsfeed.dto.post.CreateRePostResponse;
import com.example.newsfeed.dto.post.PostRequestDto;
import com.example.newsfeed.dto.post.RepostResponseDto;
import com.example.newsfeed.entity.Post;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RepostService {

    private final UserService userService;
    private final PostService postService;

    public CreateRePostResponse toggleRepost(PostRequestDto requestDto, String email, Long originalPostId) {
        User user = userService.findByEmail(email);

        Post originalPost = postService.findById(originalPostId);
        Post post = new Post(user, requestDto.getContent(), originalPost);

        postService.save(post);
        return CreateRePostResponse.from(post);

    }

    public RepostResponseDto updateRepost(Long repostId, PostRequestDto requestDto, String email) {
        Long userId = getUserIdByEmail(email);
        Post repost = postService.findById(repostId);

        validatePostOwnership(repost, userId);

        repost.updatePost(requestDto.getContent());

        return RepostResponseDto.from(repost,repost.getOriginalPost());
    }

    public void deleteRepost(Long repostId, String email) {
        Long userId = getUserIdByEmail(email);
        Post repost = postService.findById(repostId);

        validatePostOwnership(repost, userId);

        repost.getOriginalPost().decrementRepostCount();
        postService.delete(repost);
    }

    private Long getUserIdByEmail(String email) {
        return userService.findUserIdByEmail(email);
    }

    private void validatePostOwnership(Post post, Long userId) {
        if (!post.getUser().getId().equals(userId)) {
            throw new ForbiddenException("이 게시글에 접근 권한이 없습니다.");
        }
    }

    private boolean isRepost(Long userId, Long originalPostId) {
        return postService.isRepost(userId, originalPostId);
    }

    private Post getRelatedRepost(Long userId, Long originalPostId) {
        return postService.getRelatedRepost(userId, originalPostId);
    }
}

