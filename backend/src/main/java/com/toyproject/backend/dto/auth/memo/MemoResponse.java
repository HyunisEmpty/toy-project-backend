package com.toyproject.backend.dto.auth.memo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemoResponse {

    private Long id;

    private String title;

    private String content;

    private String writer;

    private LocalDateTime createdAt;

}