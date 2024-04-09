package com.example.shoppingmall.domain.entity;

import com.example.shoppingmall.domain.CategoryName;
import com.example.shoppingmall.web.controller.request.ItemRegisterRequest;
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
    @Column(name = "category_id")
    @Enumerated(EnumType.STRING)
    private CategoryName categoryName; // 상품 종류
    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image; // 상품 이미지

    @ManyToOne
    @JoinColumn(name = "editorId")
    private User user;

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

    public void setCategoryName(CategoryName categoryName){
        this.categoryName = categoryName;
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
