package ru.otus.hw02;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.hw02.i18n.MessageSourceWrapperImpl;
import ru.otus.hw02.interfaces.services.TestsExecutorService;

import java.util.Locale;

@Configuration
@ComponentScan
@PropertySource("classpath:main.properties")
public class Main {

    @Bean
    public MessageSourceWrapperImpl messageSourceWrapper(@Value("#{T(java.util.Locale).forLanguageTag('${locale.language.tag}')}") Locale locale){
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");

        return new MessageSourceWrapperImpl(ms, locale);
    }

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
        TestsExecutorService testsExecutorService = ctx.getBean(TestsExecutorService.class);
        testsExecutorService.executeTest(1L);
    }
}
