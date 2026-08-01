package com.toyproject.backend.repository;

import com.toyproject.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 로그인 시 입력한 아이디로 사용자를 조회하기 위한 메서드. 사용자가 존재하지 않을 수도 있으므로 Optional을 반환
    Optional<User> findByUsername(String username);

    // 회원 가입시 동일한 아이디가 이미 존재하는지 확인하기 위한 메서드
    boolean existsByUsername(String username);
}
