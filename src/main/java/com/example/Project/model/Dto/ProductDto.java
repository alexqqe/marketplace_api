package com.example.Project.model.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductDto {
    private String name;
    private String description;
    private int price;
    private String category;
}
