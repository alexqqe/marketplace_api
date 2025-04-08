package com.example.Project.services;

import com.example.Project.model.Dto.ProductDto;
import com.example.Project.model.entity.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14.1")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }


    @Autowired
    private TestRestTemplate restTemplate;

    private static long createdProductId;


    private ProductDto createSampleProductDto() {
        return new ProductDto("Test Product", "Test Description", 999, "Test Category");
    }

    @Test
    @Order(1)
    void testCreateProduct() {
        ProductDto productDto = createSampleProductDto();
        ResponseEntity<Void> response = restTemplate.postForEntity("/products", productDto, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(2)
    void testReadAllProducts() {
        ResponseEntity<Product[]> response = restTemplate.getForEntity("/products", Product[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);

        createdProductId = response.getBody()[0].getId(); // сохраняем ID
    }

    @Test
    @Order(3)
    void testReadProductById() {
        ResponseEntity<Product> response = restTemplate.getForEntity("/products" + "/" + createdProductId, Product.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Product", response.getBody().getName());
    }

    @Test
    @Order(4)
    void testUpdateProduct() {
        ProductDto updated = new ProductDto("Updated Name", "Updated Desc", 777, "Updated Cat");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductDto> request = new HttpEntity<>(updated, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                "/products" + "/" + createdProductId,
                HttpMethod.PUT,
                request,
                Void.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Product updatedProduct = restTemplate.getForObject("/products" + "/" + createdProductId, Product.class);
        assertEquals("Updated Name", updatedProduct.getName());
    }

    @Test
    @Order(5)
    void testDeleteProduct() {
        ResponseEntity<Void> response = restTemplate.postForEntity("/products" + "/" + createdProductId, null, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Проверим, что теперь продукт не читается
        ResponseEntity<String> readResponse = restTemplate.getForEntity("/products" + "/" + createdProductId, String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, readResponse.getStatusCode()); // queryForObject упадёт
    }
}
