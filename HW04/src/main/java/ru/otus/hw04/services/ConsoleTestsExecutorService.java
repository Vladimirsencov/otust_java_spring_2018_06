package ru.otus.hw04.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.hw04.interfaces.services.DataStorageService;
import ru.otus.hw04.interfaces.i18n.MessageSourceWrapper;
import ru.otus.hw04.interfaces.services.TestsExecutorService;
import ru.otus.hw04.models.Question;
import ru.otus.hw04.models.QuestionAnswer;
import ru.otus.hw04.models.Test;

import java.util.Collections;
import java.util.List;

@Service("testsExecutorService")
public class ConsoleTestsExecutorService implements TestsExecutorService {

    private static final String MSG_WELCOME_TO_TESTING = "welcome.to.testing";
    private static final String ERR_TEST_DOES_NOT_EXISTS = "err.test.does.not.exists";
    private static final String ERR_TEST_ALREADY_STARTED = "err.test.already.started";
    private static final String ERR_TEST_NOT_STARTED = "err.test.not.started";
    private static final String ERR_LOW_NAME_LEN = "err.low.name.len";
    private static final String ERR_ANSWER_DOES_NOT_MATCH_PATTERN = "err.answer.does.not.match.pattern";
    private static final String MSG_TESTING_PASSED = "testing.passed";
    private static final String MSG_TESTING_FAILED = "testing.failed";
    private static final String MSG_ANSWERED_QUESTIONS_COUNT = "answered.questions.count";
    private static final String MSG_INPUT_CORRECT_ANSWER_NUMBER = "input.correct.answer.number";
    private static final String INPUT_CORRECT_ANSWER_NUMBERS = "input.correct.answer.numbers";

    private final MessageSourceWrapper messageSourceWrapper;
    private final DataStorageService dataStorageService;

    private String testerName;
    private Test currentTest;
    private boolean[] answers;

    @Autowired
    public ConsoleTestsExecutorService(DataStorageService dataStorageService, MessageSourceWrapper messageSourceWrapper) {
        this.dataStorageService = dataStorageService;
        this.messageSourceWrapper = messageSourceWrapper;
    }

    private boolean checkTestingStarted() {
        if (currentTest == null) {
            System.err.println(messageSourceWrapper.getMsg(ERR_TEST_NOT_STARTED));
            return false;
        }
        return true;
    }

    private void outputCurrentTest(){
        System.out.println(messageSourceWrapper.getMsg(MSG_WELCOME_TO_TESTING, currentTest.getName()));
        for (int j = 0; j < currentTest.getQuestions().size(); j++) {
            Question question = currentTest.getQuestions().get(j);
            Collections.shuffle(question.getAnswers());

            System.out.println(String.format("%d. %s", j + 1, question.getText()));
            System.out.println(messageSourceWrapper.getMsg(question.isRadio()? MSG_INPUT_CORRECT_ANSWER_NUMBER: INPUT_CORRECT_ANSWER_NUMBERS));
            for (int i = 0; i < question.getAnswers().size(); i++) {
                QuestionAnswer answer = question.getAnswers().get(i);
                System.out.println(String.format("%d) %s", i, answer.getText()));
            }
            System.out.println();
        }
    }

    private int calcCorrectAnswers(){
        int correctAnswers = 0;
        for (int i = 0; i < answers.length; i++) {
            correctAnswers += answers[i]? 1: 0;
        }
        return correctAnswers;
    }

    private boolean checkUserAnswer(String userAnswer, Question question) {
        String[] resArr = userAnswer.replaceAll(" ", "").split(",");
        if (resArr.length == 0) return false;

        int k = question.isRadio() ? 1 : resArr.length;

        boolean isCorrect = true;
        for (int i = 0; i < k; i++) {
            List<QuestionAnswer> answers = question.getAnswers();

            int index = Integer.parseInt(resArr[i]);
            isCorrect = isCorrect && (index < answers.size() && answers.get(index).isCorrect());
            if (!isCorrect) break;
        }
        return isCorrect;
    }

    private void outputResults(String name, int correctAnswers, Test test) {
        System.out.println(messageSourceWrapper.getMsg((correctAnswers >= test.getCorrectQuestionsToPass()) ? MSG_TESTING_PASSED : MSG_TESTING_FAILED));
        System.out.println(messageSourceWrapper.getMsg(MSG_ANSWERED_QUESTIONS_COUNT, name, correctAnswers, test.getQuestions().size()));
    }


    @Override
    public void startTest(long id, String testerName) throws Exception {
        if(currentTest != null) {
            System.err.println(messageSourceWrapper.getMsg(ERR_TEST_ALREADY_STARTED));
            return;
        }

        if (testerName.length() < 3) {
            System.err.println(messageSourceWrapper.getMsg(ERR_LOW_NAME_LEN));
            return;
        }

        currentTest = dataStorageService.getTest(id);
        if (currentTest == null) {
            System.err.println(messageSourceWrapper.getMsg(ERR_TEST_DOES_NOT_EXISTS));
            return;
        }

        Collections.shuffle(currentTest.getQuestions());
        answers = new boolean[currentTest.getQuestions().size()];
        this.testerName = testerName;

        outputCurrentTest();
    }

    @Override
    public void answerQuestion(int questionNumber, String answer) {
        if(!checkTestingStarted()) {
            return;
        }

        if (!answer.matches("^(?:|(?:\\d+\\,{0,1}?)*\\d+)$")) {
            System.err.println(messageSourceWrapper.getMsg(ERR_ANSWER_DOES_NOT_MATCH_PATTERN));
            return;
        }

        if (questionNumber > answers.length) {
            System.err.println(messageSourceWrapper.getMsg(ERR_ANSWER_DOES_NOT_MATCH_PATTERN));
            return;
        }
        answers[questionNumber - 1] = checkUserAnswer(answer, currentTest.getQuestions().get(questionNumber - 1));
    }

    @Override
    public void finishTest() {
        if(!checkTestingStarted()) {
            return;
        }

        int correctAnswers = calcCorrectAnswers();
        outputResults(testerName, correctAnswers, currentTest);

        answers = null;
        currentTest = null;
        testerName = null;
    }
}
