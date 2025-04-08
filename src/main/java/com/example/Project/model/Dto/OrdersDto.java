package com.example.Project.model.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class OrdersDto {
    @NotNull
    private int userId;
    private Date orderDate;
    private String status; // можно как-то enum сюда привязать
}
