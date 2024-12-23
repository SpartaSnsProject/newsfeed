package com.example.newsfeed.service;



import com.example.newsfeed.config.PassWordEncoder;
import com.example.newsfeed.dto.user.*;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.exception.user.DuplicateUsernameException;
import com.example.newsfeed.exception.user.DuplicateEmailException;
import com.example.newsfeed.exception.user.UserNotFoundException;
import com.example.newsfeed.repository.UserRepository;
import com.example.newsfeed.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Slf4j  // 이 어노테이션 추가

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PassWordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserSignupResponseDto signup(UserSignupRequestDto requestDto) {
        // 중복 검사
        if (userRepository.existsByDisplayName(requestDto.getDisplayName())) {
            throw new DuplicateUsernameException("이미 사용 중인 사용자명입니다.");
        }
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // User 엔티티 생성
        User user = User.builder()
                .username(requestDto.getUsername())
                .email(requestDto.getEmail())
                .password(encodedPassword)
                .displayName(requestDto.getDisplayName())
                .bio(requestDto.getBio())
                .birthDate(requestDto.getBirthDate())
                .build();

        // 저장
        User savedUser = userRepository.save(user);

        return UserSignupResponseDto.from(savedUser);
    }

    @Transactional
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return UserLoginResponseDto.of(user, token);
    }

    @Transactional(readOnly = true)
    public UserProfileResponseDto getUserProfile(Long id) {  // userId를 id로 변경
        log.info("Getting user profile for id: {}", id);

        User user = userRepository.findById(id)  // userId 대신 id 사용
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", id);
                    return new UserNotFoundException("존재하지 않는 사용자입니다.");
                });

        log.info("Found user: id={}, email={}",
                user.getId(),
                user.getEmail());

        if (user.isDeleted()) {
            log.warn("User is deleted: id={}", id);
            throw new IllegalArgumentException("탈퇴한 사용자입니다.");
        }

        UserProfileResponseDto responseDto = UserProfileResponseDto.from(user);
        log.info("Created response DTO: {}", responseDto);

        return responseDto;
    }
}
