package com.example.Project.services;

import com.example.Project.model.entity.OrderItems;
import com.example.Project.model.Dto.OrderItemsDto;
import com.example.Project.services.interfaces.OrderItemsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemsService implements OrderItemsInterface {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderItemsService (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<OrderItems> getOrderItemsById(int id) {
        String sql = "SELECT * FROM order_items WHERE id = ?";
        try {
            return jdbcTemplate.query(
                    sql,
                    new Object[]{id},
                    (rs, rowNum) -> {
                        OrderItems orderItems = new OrderItems();
                        orderItems.setId(rs.getInt("id"));
                        orderItems.setOrderId(rs.getInt("order_id"));
                        orderItems.setProductId(rs.getInt("product_id"));
                        orderItems.setQuantity(rs.getInt("quantity"));
                        orderItems.setPrice(rs.getBigDecimal("price"));

                        return orderItems;
                    });} catch(EmptyResultDataAccessException e){
            return null; // или можно бросить исключение, если список заказа не найден
        }
    }

    public void createOrderItem(OrderItemsDto orderItemsDto) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, orderItemsDto.getOrderId(), orderItemsDto.getProductId(), orderItemsDto.getQuantity(),
                orderItemsDto.getPrice());
    }

    public void deleteOrderItem(int order_id, int product_id) {
        String sql = "DELETE FROM order_items WHERE ((order_id = ?) AND (product_id = ?))";
        jdbcTemplate.update(sql, order_id, product_id);
    }

    public void deleteAllItemsByOrderId(int order_id) {
        String sql = "DELETE FROM order_items WHERE (order_id = ?)";
        jdbcTemplate.update(sql, order_id);
    }
}
