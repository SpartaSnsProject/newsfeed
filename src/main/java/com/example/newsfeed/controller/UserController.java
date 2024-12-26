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
    @GetMapping("/{displayName}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<UserProfileResponseDto>> getUserProfileByDisplayName(
            @PathVariable String displayName) {
        log.debug("Attempting to retrieve profile for displayName: {}", displayName);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Current authentication: {}", authentication);

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            log.error("No valid authentication found");
            throw new UnauthorizedException("인증이 필요합니다.");
        }

        // @ 기호가 없는 경우 추가
        if (!displayName.startsWith("@")) {
            displayName = "@" + displayName;
        }

        UserProfileResponseDto responseDto = userService.getUserProfileByDisplayName(displayName);
        log.debug("Successfully retrieved profile for displayName: {}", displayName);

        return ResponseEntity.ok()
                .body(ApiResponse.success("프로필 조회에 성공했습니다.", responseDto));
    }


    @Operation(
            summary = "프로필 수정",
            description = "사용자 프로필을 수정합니다. 자신의 아이디로 로그인한 diplayName만 수정기능이 동작합니다.",
            security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    @PutMapping("/{displayName}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<UserProfileResponseDto>> updateProfile(
            @PathVariable String displayName,
            @Valid @RequestBody UserUpdateRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            // displayName에 @ 추가a
            String formattedDisplayName = displayName.startsWith("@") ? displayName : "@" + displayName;

            log.debug("Attempting to update profile for displayName: {}", formattedDisplayName);

            UserProfileResponseDto responseDto = userService.updateProfileByDisplayName(formattedDisplayName, requestDto, userDetails.getUsername());
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

    @Operation(
            summary = "비밀번호 변경",
            description = "사용자의 비밀번호를 변경합니다.",
            security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    @PutMapping("/{displayName}/password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<String>> changePassword(
            @PathVariable String displayName,
            @Valid @RequestBody PasswordChangeRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            String formattedDisplayName = displayName.startsWith("@") ? displayName : "@" + displayName;
            userService.changePassword(formattedDisplayName, requestDto, userDetails.getUsername());
            return ResponseEntity.ok(ApiResponse.success("비밀번호가 성공적으로 변경되었습니다.", null));
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

    @Operation(
            summary = "회원 탈퇴",
            description = "사용자 계정을 삭제합니다.",
            security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    @DeleteMapping("/{displayName}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<String>> deleteUser(
            @PathVariable String displayName,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            String formattedDisplayName = displayName.startsWith("@") ? displayName : "@" + displayName;
            userService.deleteUser(formattedDisplayName, userDetails.getUsername());
            return ResponseEntity.ok(ApiResponse.success("회원 탈퇴가 완료되었습니다.", null));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}