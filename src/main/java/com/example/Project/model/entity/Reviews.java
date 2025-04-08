package com.example.Project.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Reviews {
    private Long id;

    private Long userId;

    private Long productId;

    private Integer rating;

    private String comment;

    private LocalDateTime createdAt = LocalDateTime.now();
}