package ru.otus.hw04.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw04.interfaces.services.TestsExecutorService;

@ShellComponent
public class ExecuteTestCommands {
    private final TestsExecutorService service;

    @Autowired
    public ExecuteTestCommands(TestsExecutorService service) {
        this.service = service;
    }

    @ShellMethod("StartTest")
    public void startTest(@ShellOption  long id, @ShellOption String testerName) throws Exception {
        service.startTest(id, testerName);
    }

    @ShellMethod("AnswerQuestion")
    void answerQuestion(@ShellOption int questionNumber, @ShellOption String answer){
        service.answerQuestion(questionNumber, answer);
    }

    @ShellMethod("FinishTest")
    void finishTest(){
        service.finishTest();
    }
}
