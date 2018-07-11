package ru.otus.hw04.interfaces.services;

public interface TestsExecutorService {
    void startTest(long id, String testerName) throws Exception;
    void answerQuestion(int questionNumber, String answer);
    void finishTest();

}
