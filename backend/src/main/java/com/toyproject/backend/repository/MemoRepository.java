package com.toyproject.backend.repository;

import com.toyproject.backend.domain.Memo;
import com.toyproject.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findByUser(User user);

    @Query("""
    select m
    from Memo m
    join fetch m.user
    """)
    List<Memo> findAllWithUser();
}
