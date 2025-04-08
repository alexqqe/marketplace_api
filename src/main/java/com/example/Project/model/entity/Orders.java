package com.example.Project.model.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Orders {
    @NotNull
    private int id;
    @NotNull
    private int userId;
    private Date orderDate;
    private String status;
}
