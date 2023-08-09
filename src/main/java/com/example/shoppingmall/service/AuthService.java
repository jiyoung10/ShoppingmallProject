package com.example.shoppingmall.service;

import com.example.shoppingmall.config.jwt.JwtTokenProvider;
import com.example.shoppingmall.domain.entity.Token;
import com.example.shoppingmall.domain.entity.User;
import com.example.shoppingmall.domain.repository.TokenRepository;
import com.example.shoppingmall.domain.repository.UserRepository;
import com.example.shoppingmall.web.dto.SigninDTO;
import com.example.shoppingmall.web.dto.SignupDTO;
import com.example.shoppingmall.web.dto.TokenInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;

    @Transactional
    public SignupDTO signup(SignupDTO signupDTO) {
        User user = User.toEntity(signupDTO);
        String encPassword = bCryptPasswordEncoder.encode(signupDTO.getPassword());
        user.setPassword(encPassword);
        user.setRole("USER");
        User savedUser = userRepository.save(user);
        log.info("AuthService - savedUser : {}", savedUser);

        return SignupDTO.entityToDto(savedUser);
    }

    @Transactional
    public TokenInfoDTO signin(SigninDTO signinDTO) {
        User user = userRepository.findByUserId(signinDTO.getUserId());

        if (user == null) {
            log.error("user is not existed");
        }

        Token token = tokenRepository.save(Token.setToken(
                jwtTokenProvider.createToken(user.getUserId(), user.getRoles())));

        TokenInfoDTO tokenInfoDTO = TokenInfoDTO.entityToDto(user, token);
        log.info("AuthService - TokenInfo : {}", tokenInfoDTO);

        return tokenInfoDTO;
    }
}
