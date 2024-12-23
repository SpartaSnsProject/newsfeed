package com.example.newsfeed.controller;

import com.example.newsfeed.dto.like.PostLikeResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PostLikeController {

    @PostMapping
    public ResponseEntity<PostLikeResponse> addPostLike(HttpSession session) {
        Long id = (Long) session.getAttribute("id");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePostLike() {

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
