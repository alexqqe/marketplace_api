package com.example.Project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.Project.model.Dto.ProductDto;
import com.example.Project.model.entity.Product;
import com.example.Project.services.interfaces.ProductServiceInterface;

import jakarta.validation.Valid;

@Service
public class ProductService implements ProductServiceInterface {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addRatingById(long id, int rating) {
        Product curProduct = readProductById(id);
        int oldRating = curProduct.getRating();
        int oldRatingCount = curProduct.getCount();

        int newRating = (oldRating + rating) / (oldRatingCount + 1);
        ProductDto updatedProduct = new ProductDto(
                curProduct.getName(),
                curProduct.getDescription(),
                curProduct.getPrice(),
                curProduct.getCategory(),
                newRating,
                oldRatingCount + 1);
        updateProductById(id, updatedProduct);
    }

    @Override
    public List<Product> readAllProducts() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt("price"),
                rs.getString("category"),
                rs.getInt("count"),
                rs.getInt("rating")));
    }

    @Override
    public void createProduct(@Valid ProductDto product) {
        String sql = "INSERT INTO products (name, description, price, category) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory());
    }

    @Override
    public Product readProductById(long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("price"),
                        rs.getString("category"),
                        rs.getInt("count"),
                        rs.getInt("rating")));
    }

    @Override
    public void updateProductById(long id, @Valid ProductDto product) {
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, category = ?, count = ?, rating = ?" +
                " WHERE id = ?";
        jdbcTemplate.update(sql,
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory(),
                product.getCount(),
                product.getRating(),
                id);
    }

    @Override
    public void deleteProductById(long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
