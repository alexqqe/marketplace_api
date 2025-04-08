package com.example.Project.services;

import com.example.Project.model.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UsersServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private com.example.Project.services.UsersService usersService;

    private Users testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new Users();
        testUser.setId(1);
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPhone("1234567890");
        testUser.setAddress("Test Address");
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(testUser);

        // Act
        Users result = usersService.getUserById(1);

        // Assert
        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getName(), result.getName());
    }

    @Test
    void getUserById_ShouldReturnNull_WhenUserNotExists() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenThrow(new EmptyResultDataAccessException(1));

        // Act
        Users result = usersService.getUserById(999);

        // Assert
        assertNull(result);
    }

    @Test
    void getUserByEmail_ShouldReturnUser_WhenUserExists() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(testUser);

        // Act
        Users result = usersService.getUserByEmail("test@example.com");

        // Assert
        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
    }

    @Test
    void createUser_ShouldInsertAndReturnUser() {
        // Arrange
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any()))
                .thenReturn(1);
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(testUser);

        // Act
        Users result = usersService.createUser(testUser);

        // Assert
        assertNotNull(result);
        verify(jdbcTemplate).update(
                eq("INSERT INTO users (name, email, address, phone) VALUES (?, ?, ?, ?)"),
                eq(testUser.getName()),
                eq(testUser.getEmail()),
                eq(testUser.getAddress()),
                eq(testUser.getPhone())
        );
    }

    @Test
    void updateUserById_ShouldUpdateAndReturnUser() {
        // Arrange
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any()))
                .thenReturn(1);
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(testUser);

        // Act
        Users result = usersService.updateUserById(testUser);

        // Assert
        assertNotNull(result);
        verify(jdbcTemplate).update(
                eq("UPDATE users SET name = ?, email = ?, address = ?, phone = ? WHERE id = ?"),
                eq(testUser.getName()),
                eq(testUser.getEmail()),
                eq(testUser.getAddress()),
                eq(testUser.getPhone()),
                eq(testUser.getId())
        );
    }

    @Test
    void deleteUserById_ShouldDeleteAndReturnUser() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(testUser);
        when(jdbcTemplate.update(anyString(), anyInt()))
                .thenReturn(1);

        // Act
        Users result = usersService.deleteUserById(1);

        // Assert
        assertNotNull(result);
        verify(jdbcTemplate).update(
                eq("DELETE FROM users WHERE id = ?"),
                eq(1)
        );
    }
}
