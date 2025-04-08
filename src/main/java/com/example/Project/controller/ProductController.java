package com.example.Project.controller;

import com.example.Project.model.Dto.ProductDto;
import com.example.Project.model.entity.Product;
import com.example.Project.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<Product> readAllProducts() {
        return this.productService.readAllProducts();
    }

    @PostMapping
    public void createProduct(@Valid @RequestBody ProductDto productDto) {
        this.productService.createProduct(productDto);
    }

    @GetMapping("/{id}")
    public Product readProductById(@PathVariable long id) {
        return this.productService.readProductById(id);
    }

    @PutMapping("/{id}")
    public void updateProductById(@PathVariable long id, @Valid @RequestBody ProductDto productDto) {
        this.productService.updateProductById(id, productDto);
    }

    @PostMapping("/{id}")
    public void deleteProductById(@PathVariable long id) {
        this.productService.deleteProductById(id);
    }
}
