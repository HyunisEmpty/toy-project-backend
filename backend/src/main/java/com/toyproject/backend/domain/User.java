package com.toyproject.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
// DB 테이블과 매핑되는 클래스를 명시하는 어노테이션
@Getter
@NoArgsConstructor
@Table(name = "User")
public class User {

    // id를 PK로 설정및 DB에서 auto-increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // User 클래스 생성자
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }
}
