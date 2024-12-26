package com.example.newsfeed.service;



import com.example.newsfeed.config.PassWordEncoder;
import com.example.newsfeed.dto.user.*;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.exception.user.*;
import com.example.newsfeed.repository.UserRepository;
import com.example.newsfeed.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
        // 이메일에서 @ 앞부분을 추출하여 displayName 설정
        String emailPrefix = requestDto.getEmail().split("@")[0];
        String displayName = "@" + emailPrefix;

        // displayName 중복 검사
        if (userRepository.existsByDisplayName(displayName)) {
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
                .displayName(displayName)  // 자동 생성된 displayName 사용
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

        // 삭제된 회원 체크 추가
        if (user.isDeleted()) {
            throw new IllegalArgumentException("탈퇴한 회원입니다.");
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return UserLoginResponseDto.of(user, token);
    }

    @Transactional(readOnly = true)
    public UserProfileResponseDto getUserProfileByDisplayName(String displayName) {
        log.info("Getting user profile for displayName: {}", displayName);

        User user = userRepository.findByDisplayName(displayName)
                .orElseThrow(() -> {
                    log.error("User not found with displayName: {}", displayName);
                    return new UserNotFoundException("존재하지 않는 사용자입니다.");
                });

        log.info("Found user: id={}, email={}", user.getId(), user.getEmail());

        if (user.isDeleted()) {
            log.warn("User is deleted: displayName={}", displayName);
            throw new IllegalArgumentException("탈퇴한 사용자입니다.");
        }

        UserProfileResponseDto responseDto = UserProfileResponseDto.from(user);
        log.info("Created response DTO: {}", responseDto);

        return responseDto;
    }
    @Transactional
    public UserProfileResponseDto updateProfileByDisplayName(String displayName, UserUpdateRequestDto requestDto, String email) {
        // displayName에 @ 추가
        String formattedDisplayName = displayName.startsWith("@") ? displayName : "@" + displayName;

        log.debug("Attempting to update profile. FormattedDisplayName: {}, Email: {}", formattedDisplayName, email);

        // 1. 대상 사용자 확인
        User user = userRepository.findByDisplayName(formattedDisplayName)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
        log.debug("Found target user: email={}, displayName={}", user.getEmail(), user.getDisplayName());

        // 2. 현재 로그인한 사용자 확인
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("로그인이 필요합니다."));
        log.debug("Found current user: email={}, displayName={}", currentUser.getEmail(), currentUser.getDisplayName());

        // 3. 권한 확인 - 이메일로 비교
        if (!currentUser.getDisplayName().equals(formattedDisplayName)) {
            log.debug("Permission denied. Current user displayName: {}, Target displayName: {}",
                    currentUser.getDisplayName(), formattedDisplayName);
            throw new ForbiddenException("프로필 수정 권한이 없습니다.");
        }

        // 4. 필드 업데이트 (null이 아닌 필드만)
        if (requestDto.getUsername() != null) {
            if (requestDto.getUsername().length() < 3 || requestDto.getUsername().length() > 15) {
                throw new IllegalArgumentException("사용자명은 3-15자 사이여야 합니다.");
            }
            user.updateUsername(requestDto.getUsername());
        }
        if (requestDto.getBio() != null) {
            user.updateBio(requestDto.getBio());
        }
        if (requestDto.getProtectedTweets() != null) {
            user.updateProtectedTweets(requestDto.getProtectedTweets());
        }

        return UserProfileResponseDto.from(user);
    }

    @Transactional
    public void changePassword(String displayName, PasswordChangeRequestDto requestDto, String email) {
        // 1. 대상 사용자 확인
        User user = userRepository.findByDisplayName(displayName)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));

        // 2. 현재 로그인한 사용자 확인
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("로그인이 필요합니다."));

        // 3. 권한 확인
        if (!currentUser.getEmail().equals(user.getEmail())) {
            throw new ForbiddenException("비밀번호 변경 권한이 없습니다.");
        }

        // 4. 현재 비밀번호 확인
        if (!passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 5. 새 비밀번호 확인
        if (!requestDto.getNewPassword().equals(requestDto.getConfirmPassword())) {
            throw new IllegalArgumentException("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }

        // 6. 새 비밀번호가 현재 비밀번호와 같은지 확인
        if (requestDto.getCurrentPassword().equals(requestDto.getNewPassword())) {
            throw new IllegalArgumentException("새 비밀번호는 현재 비밀번호와 달라야 합니다.");
        }

        // 7. 비밀번호 변경
        String encodedNewPassword = passwordEncoder.encode(requestDto.getNewPassword());
        user.updatePassword(encodedNewPassword);
    }

    @Transactional
    public void deleteUser(String displayName, String email) {
        // 1. 대상 사용자 확인
        User user = userRepository.findByDisplayName(displayName)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));

        // 2. 현재 로그인한 사용자 확인
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("로그인이 필요합니다."));

        // 3. 권한 확인
        if (!currentUser.getEmail().equals(user.getEmail())) {
            throw new ForbiddenException("회원 탈퇴 권한이 없습니다.");
        }

        // 4. 이미 탈퇴한 회원인지 확인
        if (user.isDeleted()) {
            throw new IllegalArgumentException("이미 탈퇴한 회원입니다.");
        }

        // 5. 소프트 딜리트 수행
        user.setIsDeleted(true);
    }
    // 추가 (고예나)
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("존재하지 않는 사용자입니다."));
    }
    // 추가 (고예나)
    // findUserByEmail 이름 적절치못함 수정(한성우)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("존재하지 않는 사용자입니다."));
    }
    // 추가 (고예나)
    public Long findUserIdByEmail(String email) {
        User user = userRepository.findIdByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("존재하지 않는 사용자입니다."));
        return user.getId();
    }
}
