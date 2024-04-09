package com.example.shoppingmall.security;

import com.example.shoppingmall.config.EncoderConfig;
import com.example.shoppingmall.domain.Role;
import com.example.shoppingmall.domain.entity.User;
import com.example.shoppingmall.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final EncoderConfig encoderConfig;
    private final AuthService authService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthProvider authProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll() // 특정 경로에 대해 인증 없이 접근 허용
                //.antMatchers("/auth/admin/**").access("hasRole('ROLE_ADMIN')")// ADMIN 역할이 있는 사용자만 접근 허용
                .anyRequest().permitAll()
                .and().formLogin()
                //.loginPage("/signinForm")
//                .loginProcessingUrl("/signin")
//                .usernameParameter("userName")	// login에 필요한 id 값을 userName로 설정 (default는 username)
//                .passwordParameter("password")
                .and()
                .logout() // 로그아웃 활성화
                .logoutUrl("/logout") // 로그아웃 URL 지정
                .permitAll()
                .and()
                .authenticationProvider(authProvider);
        ; // 로그아웃 엔드포인트는 인증 없이 접근 허용

        return http.build();

    }

}
