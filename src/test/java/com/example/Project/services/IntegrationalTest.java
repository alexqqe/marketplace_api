package com.example.Project.services;

import com.example.Project.model.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class IntegrationalTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:14.1")
                    .withDatabaseName("testdb")
                    .withUsername("testuser")
                    .withPassword("testpass");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsersService usersService;

    @Test
    public void testCreateUser(){
        Users user = new Users();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        ResponseEntity<Users> response = testRestTemplate.postForEntity("/users", user, Users.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        String email = response.getBody().getEmail();
        Users savedUser = usersService.getUserByEmail(email);

        assertNotNull(savedUser, "Пользователь не найден в базе данных!");
        assertEquals("John Doe", savedUser.getName(), "Имя пользователя не совпадает!");
        assertEquals(email, savedUser.getEmail(), "Email пользователя не совпадает!");
    }

    @Test void testDeleteUser(){
        Users user = new Users();

        user.setName("My Name");
        user.setEmail("My@example.Name");

        ResponseEntity<Users> response = testRestTemplate.postForEntity("/users", user, Users.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        int id = response.getBody().getId();
        String url = "/users" + id;
        String email = user.getEmail();

        testRestTemplate.exchange(
                "/users/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                id
        );

        Users savedUser = usersService.getUserByEmail(email);
        assertNull(savedUser, "Пользователь не был удален");
    }

    @Test void testUpdateUser(){
        Users user = new Users();
        user.setName("Aboba aboba");
        user.setEmail("aboba@example.aboba");

        ResponseEntity<Users> response = testRestTemplate.postForEntity("/users", user, Users.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        user.setName("Alex");
        user.setAddress("Puskina street");
        user.setId(response.getBody().getId());

        testRestTemplate.patchForObject("/users", user, Users.class);

        Users updatedUser = usersService.getUserByEmail(user.getEmail());
        assertNotNull(updatedUser, "Пользователь не найден в базе данных!");
        assertEquals("Alex", updatedUser.getName(), "Имя пользователя не совпадает!");
        assertEquals(user.getAddress(), updatedUser.getAddress(), "Email пользователя не совпадает!");
    }
}
