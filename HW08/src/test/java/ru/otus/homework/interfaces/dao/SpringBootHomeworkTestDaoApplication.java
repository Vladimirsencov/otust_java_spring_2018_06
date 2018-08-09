package ru.otus.homework.interfaces.dao;

import com.mongodb.MongoClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

@SpringBootApplication
@EntityScan(basePackages = {"ru.otus.homework.models"})
public class SpringBootHomeworkTestDaoApplication {

    private static final String MONGO_DB_URL = "localhost";
    private static final String MONGO_DB_NAME = "embeded_db";
    private static final int PORT = 12345;

    @Bean
    @DependsOn("mongoClient")
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, MONGO_DB_NAME);
    }

    @Bean(destroyMethod = "close")
    @DependsOn("embeddedMongoDbServerWrapper")
    public MongoClient mongoClient() throws IOException {
        return new MongoClient(MONGO_DB_URL, PORT);
    }

    @Bean(destroyMethod = "stop", initMethod = "start")
    public EmbeddedMongoDbServerWrapper embeddedMongoDbServerWrapper() {
        return new EmbeddedMongoDbServerWrapper(MONGO_DB_URL, PORT);
    }

}
