package com.toyproject.backend.controller;

import com.toyproject.backend.dto.auth.SignupRequest;
import com.toyproject.backend.dto.auth.SignupResponse;
import com.toyproject.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// REST API를 처리하는 컨트롤러임을 Spring에 알려주는 어노테이션
@RestController
// 컨트롤러 공통 URL
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // RequestBody 어노테이션은 클라이언트가 보낸 JSON 데이터를 SignupRequest 객체로 변환해주는 역할
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {

        SignupResponse response = authService.signup(request);

        // ResponseEntity는 HTTP 응답 전체를 표현하는 객체이다.
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
