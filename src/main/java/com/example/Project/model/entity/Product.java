package com.example.Project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    private final long id;
    private String name;
    private String description;
    private int price;
    private String category;
    private int rating;
    private int count; //кол-во пользователей оставивших рейтинг товара
}
