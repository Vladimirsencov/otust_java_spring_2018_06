package ru.otus.hw06;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.hw06.i18n.MessageSourceWrapperImpl;
import ru.otus.hw06.interfaces.i18n.MessageSourceWrapper;

import java.sql.SQLException;

@SpringBootApplication
public class Hw06Application {
    @Bean
    public MessageSourceWrapper messageSourceWrapper(@Autowired ApplicationSettings settings) {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
        return new MessageSourceWrapperImpl(ms, settings.getLocale());
    }

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Hw06Application.class, args);
    }
}
