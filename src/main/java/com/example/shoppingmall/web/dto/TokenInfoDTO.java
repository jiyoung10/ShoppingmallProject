package com.example.shoppingmall.web.dto;

import com.example.shoppingmall.domain.entity.Token;
import com.example.shoppingmall.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfoDTO {

    @Column(name = "user_id")
    private String userId;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "refresh_token")
    private String refreshToken;

    public static TokenInfoDTO entityToDto(User user, Token token){
        return new TokenInfoDTO(
                user.getUserId(),
                user.getPassword(),
                user.getName(),
                user.getPhoneNumber(),
                user.getEmail(),
                token.getAccessToken(),
                token.getRefreshToken()
        );
    }

}
