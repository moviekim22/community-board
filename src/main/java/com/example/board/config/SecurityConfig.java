package com.example.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 스프링 설정 클래스라는 뜻
@EnableWebSecurity
public class SecurityConfig {

    // 필터 체인 정의
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                // 로그인, 회원가입 페이지는 인증 없이 접근 가능
                .requestMatchers("/", "/post/list", "/user/login", "/user/signup", "/h2-console/**").permitAll()
                // 그 외 모든 요청은 인증 필요
                .anyRequest().authenticated())
                // "/h2-console/**"요청은 CSRF 보호를 적용하지 않음
                .csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                //브라우저의 X-Frame-Option 헤더 설정(H2 콘솔은 iframe 기반이라 SAMEORIGIN으로 설정해야 함)
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                // 사용자 정의 로그인 페이지 사용
                .formLogin((formLogin) -> formLogin
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/post/list"))
                //로그아웃 설정
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // 로그아웃 처리 URL
                        .logoutSuccessUrl("/")     // 로그아웃 성공 후 이동할 URL
                        .invalidateHttpSession(true) // 세션 무효화
                );

        return http.build();
    }

    // 비밀번호를 BCrypt 해시 방식으로 암호화/검증하는 인코더 등록
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}