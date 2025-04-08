package com.example.Project.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.example.Project.model.Dto.ProductDto;
import com.example.Project.model.entity.Product;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ProductService productService;

    private ProductDto testProductDto;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProductDto = new ProductDto("Test Product", "Test Description", 100, "Test Category");
        testProduct = new Product(1L, "Test Product", "Test Description", 100, "Test Category");
    }

    @SuppressWarnings("unchecked")
    @Test
    void readAllProducts_WithExistingProducts_True() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(Arrays.asList(testProduct));

        List<Product> result = productService.readAllProducts();

        assertFalse(result.isEmpty(), "Should return non-empty list");
        assertEquals(testProduct, result.get(0));
    }

    @SuppressWarnings("unchecked")
    @Test
    void readProductById_WithValidId_True() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(testProduct);

        Product result = productService.readProductById(1L);

        assertEquals(testProduct, result);
    }

    @Test
    void createProduct_WithValidDto_True() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any()))
                .thenReturn(1);

        productService.createProduct(testProductDto);

        verify(jdbcTemplate).update(
                eq("INSERT INTO products (name, description, price, category) VALUES (?, ?, ?, ?)"),
                eq(testProductDto.getName()),
                eq(testProductDto.getDescription()),
                eq(testProductDto.getPrice()),
                eq(testProductDto.getCategory()));
    }

    @Test
    void updateProductById_WithValidIdAndDto_True() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), anyLong()))
                .thenReturn(1);

        productService.updateProductById(1L, testProductDto);

        verify(jdbcTemplate).update(
                eq("UPDATE products SET name = ?, description = ?, price = ?, category = ? WHERE id = ?"),
                eq(testProductDto.getName()),
                eq(testProductDto.getDescription()),
                eq(testProductDto.getPrice()),
                eq(testProductDto.getCategory()),
                eq(1L));
    }

    @Test
    void deleteProductById_WithValidId_True() {
        when(jdbcTemplate.update(anyString(), anyLong()))
                .thenReturn(1);

        productService.deleteProductById(1L);

        verify(jdbcTemplate).update(
                eq("DELETE FROM products WHERE id = ?"),
                eq(1L));
    }

    @SuppressWarnings("unchecked")
    @Test
    void readProductById_WithInvalidId_ThrowsException() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq(999L)))
                .thenThrow(new RuntimeException("Product not found"));

        assertThrows(RuntimeException.class, () -> {
            productService.readProductById(999L);
        });
    }
}