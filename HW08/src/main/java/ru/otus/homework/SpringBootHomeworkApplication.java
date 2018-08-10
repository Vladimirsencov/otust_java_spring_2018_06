package ru.otus.homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework.i18n.MessageSourceWrapperImpl;
import ru.otus.homework.interfaces.i18n.MessageSourceWrapper;
import ru.otus.homework.settings.ApplicationSettings;

import javax.annotation.PostConstruct;
import java.sql.SQLException;

@SpringBootApplication
public class SpringBootHomeworkApplication {
    @Bean
    public MessageSourceWrapper messageSourceWrapper(@Autowired ApplicationSettings settings) {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
        return new MessageSourceWrapperImpl(ms, settings.getLocale());
    }

    private @Autowired MongoTemplate t;

    @PostConstruct
    private void dropDatabase() {
        t.getDb().drop();
    }

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(SpringBootHomeworkApplication.class, args);
    }
}
