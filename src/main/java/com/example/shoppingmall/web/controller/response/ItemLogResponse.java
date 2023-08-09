package com.example.shoppingmall.web.controller.response;

import com.example.shoppingmall.domain.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemLogResponse {
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

    public static ItemLogResponse EntityToDTO(Item item) {
        return new ItemLogResponse(
                item.getTitle(),
                item.getContent(),
                item.getPrice(),
                item.getStock(),
                item.isSoldOut()
        );
    }

}
