package com.example.newsfeed.controller;

import com.example.newsfeed.dto.common.ApiResponse;
import com.example.newsfeed.dto.user.UserLoginRequestDto;
import com.example.newsfeed.dto.user.UserLoginResponseDto;
import com.example.newsfeed.dto.user.UserSignupRequestDto;
import com.example.newsfeed.dto.user.UserSignupResponseDto;
import com.example.newsfeed.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// UserController.java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserSignupResponseDto>> signup(
            @Valid @RequestBody UserSignupRequestDto requestDto) {
        UserSignupResponseDto responseDto = userService.signup(requestDto);
        return ResponseEntity.ok()
                .body(ApiResponse.success("회원가입이 성공적으로 완료되었습니다.", responseDto));
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserLoginResponseDto>> login(
            @Valid @RequestBody UserLoginRequestDto requestDto) {
        UserLoginResponseDto responseDto = userService.login(requestDto);
        return ResponseEntity.ok()
                .body(ApiResponse.success("로그인에 성공했습니다.", responseDto));
    }
}
