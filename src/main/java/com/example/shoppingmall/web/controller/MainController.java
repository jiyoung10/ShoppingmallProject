package com.example.shoppingmall.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/main")
@RestController
public class MainController {
    @GetMapping("/")
    public String main(){
        log.info("main Controller access success");
        return "접속 성공";
    }
}
