package com.toyproject.backend.jwt;

import com.toyproject.backend.security.CustomUserDetails;
import com.toyproject.backend.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    // 해당 함수 실행은 필터 등록시 spring security함수가 자동으로 실행시킨다.
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 클라이언트가 보낸 헤더를 호출하는 부분
        String authorization = request.getHeader("Authorization");

        // JWT가 존재 하는지 확인
        if (authorization != null && authorization.startsWith("Bearer ")) {

            // Bearer 제거 후 JWT만 남깁니다.
            String token = authorization.substring(7);

            // JWT 검증진행 -> JWT의 진위 여부를 확인하는 부분
            if (jwtProvider.validateToken(token)) {

                // username 추출
                String username = jwtProvider.getUsername(token);

                CustomUserDetails userDetails =
                        (CustomUserDetails) customUserDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}