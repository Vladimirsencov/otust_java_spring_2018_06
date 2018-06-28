package ru.otus.hw01;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw01.interfaces.services.TestsExecutorService;

public class Main {

    private static final String SPRING_CONTEXT_XML = "spring-context.xml";

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(SPRING_CONTEXT_XML);
        TestsExecutorService testsExecutorService = ctx.getBean(TestsExecutorService.class);
        testsExecutorService.executeTest(1L);
    }
}
