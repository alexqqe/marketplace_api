package com.example.Project.controller;

import com.example.Project.model.Dto.OrderItemsDto;
import com.example.Project.model.Dto.OrdersDto;
import com.example.Project.model.entity.Orders;
import com.example.Project.services.OrderItemsService;
import com.example.Project.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderItemsService orderItemsService;

    @PostMapping("/order")
    public void createOrder(@Valid @RequestBody OrdersDto ordersDto) {
        this.orderService.createOrder(ordersDto);
    }

    @PostMapping("/items")
    public void addItems(@Valid @RequestBody OrderItemsDto orderItemsDto) {
        this.orderItemsService.createOrderItem(orderItemsDto);
    }
    
}
