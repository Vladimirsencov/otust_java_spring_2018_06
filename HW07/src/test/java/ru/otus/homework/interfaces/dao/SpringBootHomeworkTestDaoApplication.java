package ru.otus.homework.interfaces.dao;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"ru.otus.homework.models"})
public class SpringBootHomeworkTestDaoApplication {
}
