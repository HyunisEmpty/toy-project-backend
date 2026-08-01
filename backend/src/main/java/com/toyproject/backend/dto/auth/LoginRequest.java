package com.toyproject.backend.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
// 매개변수 없는 기본 생성자를 자동 생성 -> lombok 어노테이션
@NoArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}
