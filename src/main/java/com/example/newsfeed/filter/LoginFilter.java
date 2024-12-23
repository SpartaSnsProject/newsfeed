package com.example.newsfeed.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LoginFilter implements Filter {
    // 인증이 필요없는 경로들을 추가
    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/api/users/login",
            "/api/users/signup",
            "/swagger-ui/**",        // Swagger UI 경로
            "/v3/api-docs/**",       // Swagger API docs
            "/swagger-resources/**",  // Swagger 리소스
            "/webjars/**"            // Swagger WebJar
    );
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();

        // 제외할 경로인지 확인
        boolean isExcludedPath = EXCLUDED_PATHS.stream()
                .anyMatch(p -> pathMatcher.match(p, path));

        if (isExcludedPath) {
            chain.doFilter(request, response);
            return;
        }

        // 세션 체크
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        chain.doFilter(request, response);
    }
}