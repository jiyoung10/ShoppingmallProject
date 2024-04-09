package com.example.shoppingmall.web.controller.request;

import com.example.shoppingmall.domain.CategoryName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;

@Setter
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
    @Column(name = "category")
    private CategoryName categoryName; // 상품 종류
    @Column(name = "tag")
    private String[] tag; // 해시태그
//    @Column(name = "file") // 타입 특성상 컬럼을 사용하면 값을 받아올 수 없음
    private MultipartFile file;
}
