package com.example.Project.services;

import com.example.Project.model.entity.Users;
import com.example.Project.services.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UsersService implements UserServiceInterface {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Users getUserById(int id) {
        String sql = "SELECT id, name, email, address, phone FROM users WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{id},
                    (rs, rowNum) -> {
                        Users user = new Users();
                        user.setId(rs.getInt("id"));
                        user.setName(rs.getString("name"));
                        user.setEmail(rs.getString("email"));
                        user.setPhone(rs.getString("phone"));
                        user.setAddress(rs.getString("address"));

                        return user;
                    });} catch(EmptyResultDataAccessException e){
            return null; // или можно бросить исключение, если пользователь не найден
        }
    }

    public Users getUserByEmail(String email) {
        String sql = "SELECT id, name, email, address, phone FROM users WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{email},
                    (rs, rowNum) -> {
                        Users user = new Users();
                        user.setId(rs.getInt("id"));
                        user.setName(rs.getString("name"));
                        user.setEmail(rs.getString("email"));
                        user.setPhone(rs.getString("phone"));
                        user.setAddress(rs.getString("address"));

                        return user;
                    });} catch(EmptyResultDataAccessException e){
            return null; // или можно бросить исключение, если пользователь не найден
        }
    }

    public Users updateUserById(Users users){
        String sql = "UPDATE users SET name = ?, email = ?, address = ?, phone = ? WHERE id = ?";
        jdbcTemplate.update(sql, users.getName(), users.getEmail(),
                users.getAddress(), users.getPhone(),users.getId());
        return this.getUserById(users.getId());
    }

    public Users deleteUserById(int id){
        Users user = this.getUserById(id);
        String sql = "DELETE FROM users WHERE id = ?";
        this.jdbcTemplate.update(sql, id);
        return user;
    }

    public Users createUser(Users user){
        String sql = "INSERT INTO users (name, email, address, phone) VALUES (?, ?, ?, ?)";
        this.jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getAddress(), user.getPhone());
        return this.getUserByEmail(user.getEmail());
    }
}
