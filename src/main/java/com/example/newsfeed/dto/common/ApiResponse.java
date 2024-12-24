package com.example.newsfeed.dto.common;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    // API 응답의 기본 구조를 정의하는 제네릭 클래스
    private final boolean success;  // 성공/실패 여부
    private final String message;   // 응답 메시지
    private final T data;          // 실제 응답 데이터 (제네릭 타입)

    private ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // 성공 응답 생성 메서드
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    // 에러 응답 생성 메서드
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }
}