package com.example.Project.model.Dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReviewsDto {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
    private String userName;
}

@Data
public class CreateReviewRequest {
    private Long productId;
    private Integer rating;
    private String comment;
}