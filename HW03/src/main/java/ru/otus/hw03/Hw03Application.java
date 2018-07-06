package ru.otus.hw03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.hw03.i18n.MessageSourceWrapperImpl;
import ru.otus.hw03.interfaces.services.TestsExecutorService;

import java.util.Locale;

@SpringBootApplication
public class Hw03Application {

    @Bean
    public MessageSourceWrapperImpl messageSourceWrapper(@Autowired  ApplicationSettings settings) {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
        return new MessageSourceWrapperImpl(ms, settings.getLocale());
    }


    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(Hw03Application.class, args);
        TestsExecutorService testsExecutorService = ctx.getBean(TestsExecutorService.class);
        testsExecutorService.executeTest(1L);
    }
}
