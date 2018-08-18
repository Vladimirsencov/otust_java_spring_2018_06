package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.homework.i18n.MessageSourceWrapperImpl;
import ru.otus.homework.interfaces.i18n.MessageSourceWrapper;

import java.sql.SQLException;
import java.util.Locale;

@SpringBootApplication
public class SpringBootHomeworkApplication {
/*
    @Bean
    public MessageSourceWrapper messageSourceWrapper() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
        return new MessageSourceWrapperImpl(ms, Locale.forLanguageTag("ru_RU"));
    }
*/

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(SpringBootHomeworkApplication.class, args);
    }
}
