package com.example.Project.model.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Favorites {
    private Long id;
    private Long userId;
    private Long productId;
}