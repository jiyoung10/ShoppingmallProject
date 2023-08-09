package com.example.shoppingmall.domain.entity;

import com.example.shoppingmall.web.controller.request.ItemRegisterRequest;
import com.example.shoppingmall.web.dto.ItemDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "item")
public class Item extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title; // 상품명
    private String content; // 상품 설명
    private int price; // 가격
    private int stock; // 재고
    private boolean isSoldOut; // 매진 여부
    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image; // 상품 이미지

    private Item(String title, String content, int price, int stock, boolean isSoldOut){
        this.title = title;
        this.content = content;
        this.price = price;
        this.stock = stock;
        this.isSoldOut = isSoldOut;
    }

    public static Item toEntity(ItemRegisterRequest itemRegisterRequest) {
        return new Item(
                itemRegisterRequest.getTitle(),
                itemRegisterRequest.getContent(),
                itemRegisterRequest.getPrice(),
                itemRegisterRequest.getStock(),
                itemRegisterRequest.isSoldOut()
                );
    }

    public void updateImage(Image image) {
        this.image = image;
    }

    public void updateItem(Item item) {
        this.title = item.getTitle();
        this.content = item.getContent();
        this.price = item.getPrice();
        this.stock = item.getStock();
        this.isSoldOut = item.isSoldOut();
    }
}
