package com.example.shoppingmall.web.controller;

import com.example.shoppingmall.domain.Role;
import com.example.shoppingmall.domain.entity.User;
import com.example.shoppingmall.service.AuthService;
import com.example.shoppingmall.service.ItemService;
import com.example.shoppingmall.web.controller.response.ItemLogResponse;
import com.example.shoppingmall.web.dto.SigninDTO;
import com.example.shoppingmall.web.dto.SignupDTO;
import com.example.shoppingmall.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {
    private final AuthService authService;
    private final ItemService itemService;

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @GetMapping("/signinForm")
    public String signinForm() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.info("GET signinForm authentication : {}", authentication);
//        if (authentication instanceof AnonymousAuthenticationToken) {
//            return "signin";
//        }
//        return "auth/main";
        return "signin";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute SignupDTO signupDTO) {
        SignupDTO signupInfo = authService.signup(signupDTO);

        if (signupInfo == null) {
            return "signup";
        }
        return "main";
    }

    @GetMapping("/adminSignup")
    public String adminSignupForm() {
        return "auth/admin/signup";
    }

    @PostMapping("/adminSignup")
    public String adminSignup(@ModelAttribute SignupDTO signupDTO) {
        SignupDTO signupInfo = authService.adminSignup(signupDTO);

        if (signupInfo == null) {
            return "auth/admin/signup";
        }
        return "signin";
    }

    @PostMapping("/signin")
    public String signin(@ModelAttribute SigninDTO signinDTO, Model model) throws Exception {
        log.info("signin cont : {}", signinDTO.getUserName());
        List<ItemLogResponse> itemList = itemService.searchItemList();
        UserDTO userDTO = authService.signin(signinDTO);

        log.info("getRole test : {}", authService.getRole(signinDTO.getUserName()));

       if (Objects.equals(authService.getRole(signinDTO.getUserName()), Role.ADMIN)) {
            model.addAttribute("userInfo", userDTO);
            model.addAttribute("products", itemList);

            return "auth/admin/main";
        }

        model.addAttribute("userInfo", userDTO);
        model.addAttribute("products", itemList);
        return "auth/main";
    }


   /* @GetMapping("/admin/main")
    public String adminMain() {
        return "auth/admin/main";
    }*/

//    @PostMapping("/signup")
//    public SignupDTO signupForm(@RequestBody SignupDTO signupDTO) {
//        return authService.signup(signupDTO);
//
//    }
//
//    @PostMapping("/signin")
//    public TokenInfoDTO logout(@RequestBody SigninDTO signinDTO) {
//        return authService.signin(signinDTO);
//    }


}
