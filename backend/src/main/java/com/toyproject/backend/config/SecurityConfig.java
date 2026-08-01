package com.toyproject.backend.config;

import com.toyproject.backend.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .headers(headers ->
                        headers.frameOptions(frame -> frame.disable())
                )

                // 세션 만들지 말라고 요청
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/login.html",
                                "/signup.html",
                                "/main.html",
                                "/css/**",
                                "/js/**",
                                "/api/auth/signup",
                                "/api/auth/login",
                                "/h2-console/**"
                        ).permitAll()

                        .anyRequest().authenticated()
                )

                .formLogin(form -> form.disable())

                .httpBasic(httpBasic -> httpBasic.disable())
                // jwtAuthenticationFilter가 기본 인증 Filter보다 우선 실행되도록 설정
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}