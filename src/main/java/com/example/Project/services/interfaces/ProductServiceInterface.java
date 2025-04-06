package com.example.Project.services.interfaces;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.Project.model.Dto.ProductDto;
import com.example.Project.model.entity.Product;

import jakarta.validation.Valid;

public interface ProductServiceInterface {
    public List<Product> readAllProducts();

    public void createProduct(@Valid @RequestBody ProductDto productDto);

    public Product readProductById(@PathVariable long id);

    public void updateProductById(@PathVariable long id, @Valid ProductDto product);

    public void deleteProductById(@PathVariable long id);
}
