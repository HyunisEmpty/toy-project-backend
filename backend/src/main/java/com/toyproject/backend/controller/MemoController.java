package com.toyproject.backend.controller;

import com.toyproject.backend.domain.User;
import com.toyproject.backend.dto.auth.memo.MemoCreateRequest;
import com.toyproject.backend.dto.auth.memo.MemoResponse;
import com.toyproject.backend.security.CustomUserDetails;
import com.toyproject.backend.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memos")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @PostMapping
    public ResponseEntity<MemoResponse> createMemo(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody MemoCreateRequest request
    ) {

        User user = userDetails.getUser();

        MemoResponse response = memoService.createMemo(user, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MemoResponse>> getMemos(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        User user = userDetails.getUser();

        List<MemoResponse> response = memoService.getMemos(user);

        return ResponseEntity.ok(response);
    }
}