package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class SpringBootHomeworkApplication {
    public static void main(String[] args) throws SQLException {
        SpringApplication.run(SpringBootHomeworkApplication.class, args);
    }
}
