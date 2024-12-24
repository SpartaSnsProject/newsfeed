package com.example.newsfeed.controller;

import com.example.newsfeed.dto.common.ApiResponse;
import com.example.newsfeed.dto.user.*;
import com.example.newsfeed.exception.user.ForbiddenException;
import com.example.newsfeed.exception.user.UnauthorizedException;
import com.example.newsfeed.exception.user.UserNotFoundException;
import com.example.newsfeed.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "User", description = "사용자 관련 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserSignupResponseDto>> signup(
            @Valid @RequestBody UserSignupRequestDto requestDto) {
        UserSignupResponseDto responseDto = userService.signup(requestDto);
        return ResponseEntity.ok()
                .body(ApiResponse.success("회원가입이 성공적으로 완료되었습니다.", responseDto));
    }

    @Operation(summary = "로그인", description = "사용자 인증 후 JWT 토큰을 발급합니다.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserLoginResponseDto>> login(
            @Valid @RequestBody UserLoginRequestDto requestDto) {
        UserLoginResponseDto responseDto = userService.login(requestDto);
        return ResponseEntity.ok()
                .body(ApiResponse.success("로그인에 성공했습니다.", responseDto));
    }

    @Operation(
            summary = "프로필 조회",
            description = "사용자 ID로 프로필을 조회합니다.",
            security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    @GetMapping("/{id}")  // userId를 id로 변경
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<UserProfileResponseDto>> getUserProfile(
            @PathVariable Long id) {  // userId를 id로 변경
        log.debug("Attempting to retrieve profile for id: {}", id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Current authentication: {}", authentication);

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            log.error("No valid authentication found");
            throw new UnauthorizedException("인증이 필요합니다.");
        }

        UserProfileResponseDto responseDto = userService.getUserProfile(id);
        log.debug("Successfully retrieved profile for id: {}", id);

        return ResponseEntity.ok()
                .body(ApiResponse.success("프로필 조회에 성공했습니다.", responseDto));
    }

    @Operation(
            summary = "프로필 수정",
            description = "사용자 프로필을 수정합니다.",
            security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    @PutMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<UserProfileResponseDto>> updateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UserUpdateRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            UserProfileResponseDto responseDto = userService.updateProfile(userId, requestDto, userDetails.getUsername());
            return ResponseEntity.ok(ApiResponse.success("프로필이 성공적으로 수정되었습니다.", responseDto));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}