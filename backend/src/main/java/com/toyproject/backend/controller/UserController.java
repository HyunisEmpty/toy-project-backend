package com.toyproject.backend.controller;

import com.toyproject.backend.domain.User;
import com.toyproject.backend.dto.auth.user.UserMeResponse;
import com.toyproject.backend.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<UserMeResponse> me(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        User user = userDetails.getUser();

        UserMeResponse response = new UserMeResponse(
                user.getId(),
                user.getUsername(),
                user.getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }
}
