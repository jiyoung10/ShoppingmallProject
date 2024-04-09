package com.example.shoppingmall.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tagName")
    private String tagName;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private Tag(String tagName, Item item) {
        this.tagName = tagName;
        this.item = item;
    }

    public static Tag toEntity(String tagName, Item item) {
       return new Tag(
                tagName,
                item
        );
    }

    public Tag updateTag(String tagName, Item item) {
        return new Tag(
                tagName,
                item
        );
    }

}
