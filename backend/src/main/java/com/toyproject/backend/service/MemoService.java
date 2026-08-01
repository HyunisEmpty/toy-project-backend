package com.toyproject.backend.service;

import com.toyproject.backend.domain.Memo;
import com.toyproject.backend.domain.User;
import com.toyproject.backend.dto.auth.memo.MemoCreateRequest;
import com.toyproject.backend.dto.auth.memo.MemoResponse;
import com.toyproject.backend.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;

    public MemoResponse createMemo(User user, MemoCreateRequest request) {

        Memo memo = new Memo(
                request.getTitle(),
                request.getContent(),
                user
        );

        Memo savedMemo = memoRepository.save(memo);

        return new MemoResponse(
                savedMemo.getId(),
                savedMemo.getTitle(),
                savedMemo.getContent(),
                savedMemo.getCreatedAt()
        );
    }

    public List<MemoResponse> getMemos(User user) {

        List<Memo> memos = memoRepository.findByUser(user);

        return memos.stream()
                .map(memo -> new MemoResponse(
                        memo.getId(),
                        memo.getTitle(),
                        memo.getContent(),
                        memo.getCreatedAt()
                ))
                .toList();
    }

}