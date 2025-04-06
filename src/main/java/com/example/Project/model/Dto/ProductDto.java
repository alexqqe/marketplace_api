package com.example.Project.model.Dto;

import lombok.Data;

@Data
public class ProductDto {
    private long id;
    private String name;
    private String description;
    private int price;
    private String category;
    private Double averageRating;

    public static class ReviewStats {
        private Integer totalReviews;
        private Double averageRating;
    }
}