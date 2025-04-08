package com.example.Project.services.interfaces;

import com.example.Project.model.entity.OrderItems;
import com.example.Project.model.Dto.OrderItemsDto;

import java.util.List;

public interface OrderItemsInterface {
    // получение списка айтемов в заказе
    List<OrderItems> getOrderItemsById(int id);

    // создание айтема в заказе
    void createOrderItem(OrderItemsDto orderItemsDto);

    // удаление айтема в заказе
    void deleteOrderItem(int order_id, int product_id);

    // удаление всех айтом заказа (полезно при удалении заказа в целом)
    void deleteAllItemsByOrderId(int order_id);
}
