package com.toyproject.backend.dto.auth.memo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemoCreateRequest {

    private String title;

    private String content;

}
