package com.example.Project.model.Dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductDto {
    @NotBlank
    private String name;
    private String description;
    @Min(value = 0)
    private int price;
    @NotBlank
    private String category;
    @Min(value = 0)
    @Max(value = 5)
    private int rating = 0;
    private int count;
}
