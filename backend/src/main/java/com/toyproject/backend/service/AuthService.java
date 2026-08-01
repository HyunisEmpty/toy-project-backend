package com.toyproject.backend.service;

import com.toyproject.backend.domain.User;
import com.toyproject.backend.dto.auth.*;
import com.toyproject.backend.jwt.JwtProvider;
import com.toyproject.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
// final 필드에 대한 생성자를 자동으로 만들어 의존성을 주입.
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public SignupResponse signup(SignupRequest request){
        if (userRepository.existsByUsername(request.getUsername())){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // User 클래스 엔티티 생성
        User user = new User(
                request.getUsername(),
                encodedPassword
        );

        // JpaRepository 제네릭 클래스의 함수로 엔티티를 DB에 저장하는 함수
        userRepository.save(user);

        return new SignupResponse("회원가입이 완료되었습니다.");
    }

    public LoginResponse login(LoginRequest request){
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));

        // 비밀번호가 일치하는지 확인
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        // JwtProvider 클래스의 createToken함수 호출
        String accessToken = jwtProvider.createToken(user.getId(), user.getUsername());

        LoginUserResponse loginUser = new LoginUserResponse(
                user.getId(),
                user.getUsername()
        );

        return new LoginResponse(
                accessToken,
                loginUser
        );
    }
}
