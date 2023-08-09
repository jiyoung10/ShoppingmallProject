package com.example.shoppingmall.web.controller;

import com.example.shoppingmall.service.AuthService;
import com.example.shoppingmall.web.dto.SigninDTO;
import com.example.shoppingmall.web.dto.SignupDTO;
import com.example.shoppingmall.web.dto.TokenInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public SignupDTO signupForm(@RequestBody SignupDTO signupDTO) {
        return authService.signup(signupDTO);

    }

    @PostMapping("/signin")
    public TokenInfoDTO logout(@RequestBody SigninDTO signinDTO) {
        return authService.signin(signinDTO);
    }


}
