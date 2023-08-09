package com.example.shoppingmall.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageName;
    private String filePath;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private Image(String imageName, String filePath, Item item) {
        this.imageName = imageName;
        this.filePath = filePath;
        this.item = item;
    }

    public static Image of(String imageName, String filePath, Item item) {
        return new Image(imageName,
                filePath,
                item);
    }

    public void updateImage(Image image) {
        this.imageName = image.getImageName();
        this.filePath = image.getFilePath();
        this.item = image.getItem();
    }
}
