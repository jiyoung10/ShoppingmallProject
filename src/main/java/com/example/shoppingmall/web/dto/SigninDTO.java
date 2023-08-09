package com.example.shoppingmall.web.dto;

import com.example.shoppingmall.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SigninDTO {
    private String userId;
    private String password;

}
