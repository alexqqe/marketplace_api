package com.example.Project.model.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemsDto {
    @NotNull
    private int orderId;
    @NotNull
    private int productId;
    private int quantity;
    private BigDecimal price;
}
