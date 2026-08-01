package com.toyproject.backend.service;

import com.toyproject.backend.domain.User;
import com.toyproject.backend.dto.auth.SignupRequest;
import com.toyproject.backend.dto.auth.SignupResponse;
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
}
