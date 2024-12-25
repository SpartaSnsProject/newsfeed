package com.example.newsfeed.service;

import com.example.newsfeed.dto.post.PostRequestDto;
import com.example.newsfeed.dto.post.RepostResponseDto;
import com.example.newsfeed.entity.Post;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.exception.user.UnauthorizedException;
import com.example.newsfeed.repository.PostRepository;
import com.example.newsfeed.util.RepostMapper;
import com.example.newsfeed.util.postCont.PostMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RepostService {

    private final UserService userService;
    private final PostService postService;
    private final PostRepository postRepository;

    public RepostResponseDto toggleRepost(PostRequestDto requestDto, String email, Long originalPostId) {
        Long userId = getUserIdByEmail(email);
        Post originalPost = postService.getPostById(originalPostId);
        User user = userService.findById(userId);
        Post repost = originalPost.repost(user, requestDto.getContent());

        postService.save(repost);

        return RepostMapper.toDto(repost, originalPost);
    }

    public RepostResponseDto updateRepost(Long repostId, PostRequestDto requestDto, String email) {
        Long userId = getUserIdByEmail(email);
        Post repost = postService.getPostById(repostId);
        validatePostOwnership(repost, userId);

        repost.updatePost(requestDto.getContent());

        return RepostMapper.toDto(repost,repost.getOriginalPost());
    }

    public void deleteRepost(Long repostId, String email) {
        Long userId = getUserIdByEmail(email);
        Post repost = postService.getPostById(repostId);

        postService.delete(repost);
    }

    private Long getUserIdByEmail(String email) {
        return userService.findUserIdByEmail(email);
    }

    private void validatePostOwnership(Post post, Long userId) {
        if (!post.getUser().getId().equals(userId)) {
            throw new UnauthorizedException(PostMessages.UN_AUTH_UPDATE);
        }
    }
}

