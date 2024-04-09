package com.example.shoppingmall.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryName {
    DRESS("DRESS"),
    FURNITURE("FURNITURE"),
    ELECTRONICS("Electronics"),
    HOME_APPLIANCES("HOME_APPLIANCES"),
    BOOKS("BOOKS"),
    BEAUTY("BEAUTY"),
    TOYS("TOYS"),
    SPORTS("SPORTS"),
    GROCERIES("GROCERIES"),
    ACCESSORIES("ACCESSORIES");

    @Getter
    private final String CategoryName;

    public String getCategoryName() {
        return this.CategoryName;
    }

}
