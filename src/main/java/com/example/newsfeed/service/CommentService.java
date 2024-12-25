package com.example.newsfeed.service;


import com.example.newsfeed.dto.comment.RequestPatchComment;
import com.example.newsfeed.dto.comment.ResponseComment;
import com.example.newsfeed.dto.comment.RequestComment;
import com.example.newsfeed.entity.Comment;
import com.example.newsfeed.entity.Post;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.repository.CommentRepository;
import com.example.newsfeed.repository.PostRepository;
import com.example.newsfeed.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    CommentRepository commentRepository;

    UserRepository userRepository;
    PostRepository postRepository;
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }


    public ResponseComment addComment(RequestComment requestComment, String username) {
        User user = userRepository
                .findByEmail(username)
                .orElseThrow(() -> new RuntimeException("유저 못찾음"));
        String displayName = user.getDisplayName();

        if (requestComment.getCommentId() == null && requestComment.getPostId() == null) {
            throw new RuntimeException("요청 제대로 안함 익셉션");
        } else if (requestComment.getCommentId() == null) {

            Post post = postRepository.findById(requestComment
                    .getPostId())
                    .orElseThrow(() -> new RuntimeException("게시물 못찾음"));
            Comment save = commentRepository
                    .save(new Comment(displayName, post, requestComment.getContents()));

            return ResponseComment.from(save);
        } else if (requestComment.getPostId() == null) {

            Comment comment = commentRepository.findById(requestComment.getCommentId()).orElseThrow(() -> new RuntimeException("댓글을 못찾음"));
            Comment save = commentRepository.save(new Comment(displayName, comment, requestComment.getContents()));

            return ResponseComment.from(save);
        } else {
            throw new RuntimeException("둘중 하나만 입력해 익셉션");
        }
    }


    public List<ResponseComment> getCommentByPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("아 게시물 없다고"));
        List<Comment> commentList= commentRepository.findAllByPost(post);

        return commentList.stream()
                .map(ResponseComment::from)
                .toList();
    }

    public List<ResponseComment> getCommentByComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("댓글을 못찾겠음"));
        List<Comment> allByComment = commentRepository.findAllByComment(comment);

        return allByComment.stream()
                .map(ResponseComment::from)
                .toList();
    }


    public ResponseComment updateComment(RequestPatchComment comment, String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("유저못찾음"));
        Comment findComment = commentRepository.findById(comment.getCommentId()).orElseThrow(() -> new RuntimeException("댓글못찾음"));

        if (user.getDisplayName().equals(findComment.getDisplayName())) {
            findComment.setContents(comment.getContents());
            Comment save = commentRepository.save(findComment);
            return ResponseComment.from(save);
        } else {
            throw new RuntimeException("이 댓글에 권한없음 익셉션");
        }

    }

    public ResponseComment deleteComment(Long commentId, String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("유저못찾음익셉션"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("댓글못찾음익셉션"));

        if (user.getDisplayName().equals(comment.getDisplayName())) {
            commentRepository.deleteById(commentId);
            return ResponseComment.from(comment);
        } else {
            throw new RuntimeException("이 댓글에 권한없음 익셉션");
        }
    }
}
