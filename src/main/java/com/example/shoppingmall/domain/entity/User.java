package com.example.shoppingmall.domain.entity;

import com.example.shoppingmall.web.dto.SignupDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String userId;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> roles = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private User(String userId, String password, String name, String phoneNumber, String email){
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public static User toEntity(SignupDTO signupDTO){
        return new User(
                signupDTO.getUserId(),
                signupDTO.getPassword(),
                signupDTO.getName(),
                signupDTO.getPhoneNumber(),
                signupDTO.getEmail()
        );
    }

    public void setPassword(String encPassword){
        this.password = encPassword;
    }

    public void setRole(String role){
        this.roles.add(role);
    }

}
