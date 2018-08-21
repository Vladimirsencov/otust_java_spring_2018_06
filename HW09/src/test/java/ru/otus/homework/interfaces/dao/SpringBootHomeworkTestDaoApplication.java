package ru.otus.homework.interfaces.dao;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import static ru.otus.homework.TestConst.*;

@SpringBootApplication
@EntityScan(basePackages = {MODELS_PACKAGE})
@ComponentScan(basePackages = {DAO_PACKAGE})
public class SpringBootHomeworkTestDaoApplication {
}
