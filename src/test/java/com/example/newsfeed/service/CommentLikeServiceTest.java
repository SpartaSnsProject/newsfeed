package com.example.newsfeed.service;

import com.example.newsfeed.dto.like.RequestComment;
import com.example.newsfeed.entity.Comment;
import com.example.newsfeed.entity.CommentLike;
import com.example.newsfeed.repository.CommentLikeRepository;
import com.example.newsfeed.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentLikeServiceTest {

    private CommentLikeRepository commentLikeRepository;
    private CommentRepository commentRepository;
    private CommentLikeServiceImpl commentLikeService;

    @BeforeEach
    public void setUp() {
        commentLikeRepository = Mockito.mock(CommentLikeRepository.class);
        commentRepository = Mockito.mock(CommentRepository.class);
        commentLikeService = new CommentLikeServiceImpl(commentLikeRepository, commentRepository);
    }

    @Test
    public void testAddCommentLike_Success() {
        Long userId = 1L;


        verify(commentLikeRepository, times(1)).save(any(CommentLike.class));
    }
}
