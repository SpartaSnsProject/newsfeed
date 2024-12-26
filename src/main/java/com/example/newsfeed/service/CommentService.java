package com.example.newsfeed.service;


import com.example.newsfeed.dto.comment.RequestPatchComment;
import com.example.newsfeed.dto.comment.ResponseComment;
import com.example.newsfeed.dto.comment.RequestComment;
import com.example.newsfeed.entity.Comment;
import com.example.newsfeed.entity.Post;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.exception.ForbiddenException;
import com.example.newsfeed.exception.NotFoundException;
import com.example.newsfeed.repository.CommentRepository;
import com.example.newsfeed.repository.PostRepository;
import com.example.newsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;

    private final UserService userService;
    private final PostService postService;

    public ResponseComment addComment(RequestComment requestComment, String username) {
        User byEmail = userService.findByEmail(username);
        String displayName = byEmail.getDisplayName();

        if (requestComment.getCommentId() == null && requestComment.getPostId() == null) {
            throw new RuntimeException("요청 제대로 안함 익셉션");
        } else if (requestComment.getCommentId() == null) {

            Post byId = postService.findById(requestComment.getPostId());

            Comment save = commentRepository
                    .save(new Comment(displayName,byId, requestComment.getContents()));
            byId.upCommentCount();

            return ResponseComment.from(save);
        } else if (requestComment.getPostId() == null) {
            Comment comment = findById(requestComment.getCommentId());
            Comment save = commentRepository.save(new Comment(displayName, comment, requestComment.getContents()));
            comment.upReCommentCount();

            return ResponseComment.from(save);
        } else {
            throw new RuntimeException("둘중 하나만 입력해 익셉션");
        }
    }

    public List<ResponseComment> getCommentByPost(Long postId) {
        Post byId = postService.findById(postId);
        List<Comment> commentList= commentRepository.findAllByPost(byId);

        return commentList.stream()
                .map(ResponseComment::from)
                .toList();
    }

    public List<ResponseComment> getCommentByComment(Long commentId) {
        Comment comment = findById(commentId);
        List<Comment> allByComment = commentRepository.findAllByComment(comment);

        return allByComment.stream()
                .map(ResponseComment::from)
                .toList();
    }

    public ResponseComment updateComment(RequestPatchComment comment, String username) {

        User byEmail = userService.findByEmail(username);
        Comment findComment = findById(comment.getCommentId());

        if (byEmail.getDisplayName().equals(findComment.getDisplayName())) {
            findComment.setContents(comment.getContents());
            Comment save = commentRepository.save(findComment);
            return ResponseComment.from(save);
        } else {
            throw new RuntimeException("이 댓글에 권한없음 익셉션");
        }

    }

    public ResponseComment deleteComment(Long commentId, String username) {
        User user = userService.findByEmail(username);
        Comment comment = findById(commentId);

        if (user.getDisplayName().equals(comment.getDisplayName())) {

             if (comment.getPost() == null) {
                 Comment byComment = commentRepository.findByComment(comment);
                 byComment.downReCommentCount();
                 commentRepository.save(byComment);
             } else {
                Post post = postService.findById(comment.getPost().getPostId());
                post.downCommentCount();
             }
            commentRepository.deleteById(commentId);
            return ResponseComment.from(comment);
        } else {
            throw new ForbiddenException("이 댓글에 권한이 없습니다.");
        }
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("댓글못찾음익셉션"));
    }

    public void save(Comment findComment) {
        commentRepository.save(findComment);
    }
}
