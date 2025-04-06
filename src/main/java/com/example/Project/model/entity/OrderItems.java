package com.example.Project.model.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OrderItems {
    @NotNull
    private int id;
    @NotNull
    private int orderId;
    @NotNull
    private int productId;
    private int quantity;
    private BigDecimal price;
}
