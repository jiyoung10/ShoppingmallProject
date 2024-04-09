package com.example.shoppingmall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class EncoderConfig {
    @Bean
    public BCryptPasswordEncoder encoder() {
        // spring 시큐리티와 BCryptPassword는 다른 클래스에 지정해야 함.(순환참조 발생 문제때문에)
        return new BCryptPasswordEncoder();
    }
}
