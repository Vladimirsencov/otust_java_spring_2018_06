package ru.otus.hw01;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.hw01.interfaces.services.TestsExecutorService;

@Configuration
@ComponentScan
@PropertySource("classpath:main.properties")
public class Main {

    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("CP1251");
        return ms;
    }

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
        TestsExecutorService testsExecutorService = ctx.getBean(TestsExecutorService.class);
        testsExecutorService.executeTest(1L);
    }
}
