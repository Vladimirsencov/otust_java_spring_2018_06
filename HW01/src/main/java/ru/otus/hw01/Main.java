package ru.otus.hw01;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw01.interfaces.services.ITestsExecutorService;

public class Main {

    private static final String SPRING_CONTEXT_XML = "spring-context.xml";

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(SPRING_CONTEXT_XML);
        ITestsExecutorService testsExecutorService = ctx.getBean(ITestsExecutorService.class);
        testsExecutorService.executeTest(1L);
    }
}
