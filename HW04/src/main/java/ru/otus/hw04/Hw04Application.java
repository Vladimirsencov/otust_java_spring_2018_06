package ru.otus.hw04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.hw04.i18n.MessageSourceWrapperImpl;
import ru.otus.hw04.interfaces.services.TestsExecutorService;


@SpringBootApplication
public class Hw04Application {

    @Bean
    public MessageSourceWrapperImpl messageSourceWrapper(@Autowired  ApplicationSettings settings) {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
        return new MessageSourceWrapperImpl(ms, settings.getLocale());
    }


    public static void main(String[] args) throws Exception {
        SpringApplication.run(Hw04Application.class, args);
    }
}
