package com.toyproject.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
// DB 테이블과 매핑되는 클래스를 명시하는 어노테이션
@Getter
@NoArgsConstructor
public class User {

    // id를 PK로 설정및 DB에서 auto-increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    // 자바 필드(카멜) -> DB 필드(스네이크) name 매핑
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // DB에 저장되기 직전에 자동으로 해당 메서드 실행
    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

}
