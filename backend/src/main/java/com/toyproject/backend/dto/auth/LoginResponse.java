package com.toyproject.backend.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
// 모든 필드를 매개변수로 받는 생성자를 자동으로 생성한다. -> lombok 어노테이션
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private LoginUserResponse user;
}
