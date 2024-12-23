package com.example.newsfeed.service;



import com.example.newsfeed.config.PassWordEncoder;
import com.example.newsfeed.dto.user.*;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.exception.user.DuplicateUsernameException;
import com.example.newsfeed.exception.user.DuplicateEmailException;
import com.example.newsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PassWordEncoder passwordEncoder;

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
}
