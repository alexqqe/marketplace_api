package com.example.Project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/mydatabase");   // Путь к файлу БД
        dataSource.setUsername("user");
        dataSource.setPassword("securepassword1337");
        dataSource.setDriverClassName("org.postgresql.Driver");

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}

//@Configuration
//public class DatabaseConfig {
//
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.sqlite.JDBC");
//        dataSource.setDriverClassName("org.sqlite.JDBC"); // Драйвер SQLite
//        dataSource.setUrl("jdbc:sqlite::resource:db/mydatabase.db");
//        return dataSource;
//    }
//

