package com.example.shoppingmall.service;

import com.example.shoppingmall.domain.Role;
import com.example.shoppingmall.domain.entity.User;
import com.example.shoppingmall.domain.repository.UserRepository;
import com.example.shoppingmall.web.dto.SigninDTO;
import com.example.shoppingmall.web.dto.SignupDTO;

import com.example.shoppingmall.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.tokenValidTime}")
    private Long tokenValidTime;

    @Transactional
    public SignupDTO adminSignup(SignupDTO signupDTO) {
        Optional<User> userEntity = userRepository.findByUsername(signupDTO.getUserName());
        if(userEntity.isPresent()) {
            log.error("ADMIN_ID_IS_DUPLICATED");
            return null;
        }

        signupDTO.encode(bcEncode(signupDTO.getPassword()));
        User user = User.toEntity(signupDTO);
        user.setRole(Role.ADMIN);
        User savedAdmin = userRepository.save(user);
        log.info("AuthService - savedAdmin : {}", savedAdmin);

        return SignupDTO.entityToUserDto(savedAdmin);
    }

    @Transactional
    public SignupDTO signup(SignupDTO signupDTO) {
        log.info("signup service:{}", signupDTO.getUserName());

        if (userRepository.findByUsername(signupDTO.getUserName()).isPresent()) {
            log.error("USER_ID_IS_DUPLICATED");
            return null;
        }

        signupDTO.encode(bcEncode(signupDTO.getPassword()));
        User user = User.toEntity(signupDTO);
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);
        log.info("AuthService - savedUser : {}", savedUser.getUsername());

        return SignupDTO.entityToUserDto(savedUser);
    }

    public String bcEncode(String password) {
        return encoder.encode(password);
    }

    @Transactional
    public UserDTO signin(SigninDTO signinDTO) throws Exception {
        log.info("signin service : {}", signinDTO.getUserName());

        User user = userRepository.findByUsername(signinDTO.getUserName()).orElseThrow(
                () -> new Exception("USER_ID_IS_NOT_EXISTED"));

        // 패스워드 확인
        if (!encoder.matches(signinDTO.getPassword(), user.getPassword())) {
            throw new Exception("PASSWORD_IS_INVALID");
        }

        // 토큰 발급 (엑세스 , 리프레시)
        //TODO: 각 토큰들에 대한 세부 설정

        //log.info("member.getUserName@@ {}", user.getUsername());

        return UserDTO.toUserDTO(user);
    }

    public String getRole(String userName) throws Exception {
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new Exception("USER_ID_IS_NOT_EXISTED"));
        log.info("getRole : {}", user.getRoles().toString());
        return user.getRoles().toString();
    }

    public User getUserName(String userName){
        return userRepository.findByUsername(userName).orElseThrow(
                ()->new RuntimeException("USER_NAME_NOT_FIND_IN_REPO_USER"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername : {}", username);
        return userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("USER_ID_IS_NOT_EXISTED"));

    }
}
