package com.toyproject.backend.dto.auth.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserMeResponse {
    private Long id;
    private String username;
    private LocalDateTime createdAt;
}
