package com.example.Project.model.Dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReviewDto {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
    private String userName; // Для отображения имени пользователя в UI
}

@Data
public class CreateReviewRequest {
    private Long productId;
    private Integer rating;
    private String comment;
}