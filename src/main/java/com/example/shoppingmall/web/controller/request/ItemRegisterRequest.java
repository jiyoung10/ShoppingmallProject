package com.example.shoppingmall.web.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRegisterRequest {
    @Column(name = "title")
    private String title; // 상품명
    @Column(name = "content")
    private String content; // 상품 설명
    @Column(name = "price")
    private int price; // 가격
    @Column(name = "stock")
    private int stock; // 재고
    @Column(name = "isSoldOut")
    private boolean isSoldOut; // 매진 여부
    @Column(name = "tag")
    private String[] tag; // 해시태그
}
