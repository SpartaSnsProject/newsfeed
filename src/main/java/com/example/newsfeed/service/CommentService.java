package com.example.newsfeed.service;

import com.example.newsfeed.dto.comment.RequestPatchComment;
import com.example.newsfeed.dto.comment.ResponseComment;
import com.example.newsfeed.dto.like.RequestComment;

import java.util.List;

public interface CommentService {
    ResponseComment addComment(RequestComment requestComment, String username);

    List<ResponseComment> getCommentByPost(Long postId);

    List<ResponseComment> getCommentByComment(Long commentId);

    ResponseComment updateComment(RequestPatchComment comment, String username);

    ResponseComment deleteComment(Long commentId, String username);
}
