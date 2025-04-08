package com.example.Project.model.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewsDto {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
    private String userName;
}