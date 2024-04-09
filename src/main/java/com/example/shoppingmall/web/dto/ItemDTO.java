package com.example.shoppingmall.web.dto;

import com.example.shoppingmall.domain.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    @Column(name = "title")
    private String title; // 상품명
    @Column(name = "content")
    private String content; // 상품 설명
    @Column(name = "price")
    private double price; // 가격
    @Column(name = "stock")
    private int stock; // 재고
    @Column(name = "isSoldOut")
    private boolean isSoldOut; // 매진 여부
//    @Column(name = "tag")
//    private String[] tag; // 해시태그
    @Column(name = "image")
    private String image;

    public static ItemDTO fromEntity(Item item) {
        return new ItemDTO(
                item.getTitle(),
                item.getContent(),
                item.getPrice(),
                item.getStock(),
                item.isSoldOut(),
                item.getImage() != null ? item.getImage().getImageName() : "");
    }

}
