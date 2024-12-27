package com.example.newsfeed.exception;

import com.example.newsfeed.dto.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //    과정 중 발생하는 예외는 GlobalExceptionHandler가 처리합니다
    //    모든 응답은 ApiResponse 형식으로 통일되어 반환됩니다

    // 사용자명 중복 예외 처리
    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<String> handleDuplicateUsername(DuplicateUsernameException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 이메일 중복 예외 처리
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<String> handleDuplicateEmail(DuplicateEmailException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 인증이 필요한 접근
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorized(UnauthorizedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    //유저로그인 에러반환
    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ApiResponse<Void>> handleLoginFailed(LoginFailedException e) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(e.getMessage()));
    }

    // ForbiddenException 핸들러 추가
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> handleForbidden(ForbiddenException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    //대이터베이스에서 찾을수 없는 예외
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongAccessException.class)
    public ResponseEntity<String> handleWrongAccess(WrongAccessException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
