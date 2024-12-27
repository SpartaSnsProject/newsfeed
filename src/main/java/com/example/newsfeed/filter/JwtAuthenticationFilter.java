package com.example.newsfeed.filter;

import com.example.newsfeed.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = extractToken(request);
            // 보호된 엔드포인트이고 토큰이 없는 경우
            if (!shouldNotFilter(request) && token == null) {
                handleAuthenticationException(response,
                        new AuthenticationCredentialsNotFoundException("토큰이 없습니다."));
                return;
            }

            if (token != null) {
                String username = jwtUtil.getUsernameFromToken(token);
                Authentication authentication = createAuthentication(username);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Successfully authenticated user: {}", username);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Authentication error:", e);
            SecurityContextHolder.clearContext();
            handleAuthenticationException(response, e);
        }
    }

    private Authentication createAuthentication(String username) {
        UserDetails userDetails = User.builder()
                .username(username)
                .password("")
                .roles("USER")
                .build();

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    private void handleAuthenticationException(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", new Date());
        errorDetails.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        errorDetails.put("error", "Unauthorized");
        errorDetails.put("message", "인증에 실패했습니다: " + e.getMessage());

        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/users/login") ||
                path.startsWith("/api/users/signup") ||
                path.startsWith("/swagger-") ||
                path.startsWith("/v3/api-docs") ||
                path.equals("/") ||
                path.startsWith("/error");
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}