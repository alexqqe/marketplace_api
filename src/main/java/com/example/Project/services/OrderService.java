package com.example.Project.services;

import com.example.Project.model.entity.Orders;
import com.example.Project.model.Dto.OrdersDto;
import com.example.Project.services.interfaces.OrdersInterface;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements OrdersInterface {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Orders getOrderById(int id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{id},
                    (rs, rowNum) -> {
                        Orders order = new Orders();
                        order.setId(rs.getInt("id"));
                        order.setUserId(rs.getInt("user_id"));
                        order.setOrderDate(rs.getDate("order_date"));
                        order.setStatus(rs.getString("status"));

                        return order;
                    });} catch(EmptyResultDataAccessException e){
            return null; // или можно бросить исключение, если заказ не найден
        }
    }

    public Orders updateOrderById(@NotNull Orders order){
        String sql = "UPDATE orders SET user_id = ?, order_date = ?, status = ? WHERE id = ?";
        jdbcTemplate.update(sql, order.getUserId(), order.getOrderDate(),
                order.getStatus(), order.getId());
        return this.getOrderById(order.getId());
    }

    public Orders deleteOrderById(int id){
        Orders order = this.getOrderById(id);
        String sql = "DELETE FROM orders WHERE id = ?";
        this.jdbcTemplate.update(sql, id);
        return order;
    }

    public void createOrder(@NotNull OrdersDto order){
        String sql = "INSERT INTO orders (user_id, order_date, status) VALUES (?, ?, ?)";
        this.jdbcTemplate.update(sql, order.getUserId(), order.getOrderDate(), order.getStatus());
    }
}
