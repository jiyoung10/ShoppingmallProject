package com.example.shoppingmall.web.dto;

import com.example.shoppingmall.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupDTO {
    @Column(name = "userName")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "email")
    private String email;

    public static SignupDTO entityToUserDto(User user){
        return new SignupDTO(
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getPhoneNumber(),
                user.getEmail()
        );
    }

    public void encode(String password) {
        this.password = password;
    }

}
