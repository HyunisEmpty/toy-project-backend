package com.toyproject.backend.dto.auth;

// lobok 어노테이션 기반 코드 자도 완성 해주는 라이브러리
import lombok.Getter;
import lombok.NoArgsConstructor;

// 해당 어노테이션이 추가되면 컴파일 시점에 getUsername()메서드를 자동으로 생성해준다.
@Getter
@NoArgsConstructor
public class SignupRequest {
    private String username;
    private String password;
}
