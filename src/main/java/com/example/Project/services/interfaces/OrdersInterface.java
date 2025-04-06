package com.example.Project.services.interfaces;

import com.example.Project.model.entity.Orders;
import com.example.Project.model.Dto.OrdersDto;

public interface OrdersInterface {
    Orders getOrderById(int id);

    Orders updateOrderById(Orders order);

    Orders deleteOrderById(int id);

    void createOrder(OrdersDto ordersDto);
}
