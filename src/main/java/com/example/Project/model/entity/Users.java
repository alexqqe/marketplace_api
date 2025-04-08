package com.example.Project.model.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Users {
    private int id;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    private String address;
    private String phone;
}