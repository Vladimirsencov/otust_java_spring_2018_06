package ru.otus.hw03.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.hw03.interfaces.services.DataStorageService;
import ru.otus.hw03.interfaces.i18n.MessageSourceWrapper;
import ru.otus.hw03.interfaces.services.TestsExecutorService;
import ru.otus.hw03.models.Question;
import ru.otus.hw03.models.QuestionAnswer;
import ru.otus.hw03.models.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

@Service("testsExecutorService")
public class ConsoleTestsExecutorService implements TestsExecutorService {

    private static final String MSG_WELCOME_TO_TESTING = "welcome.to.testing";
    private static final String MSG_INPUT_YOUR_NAME = "input.your.name";
    private static final String ERR_LOW_NAME_LEN = "err.low.name.len";
    private static final String ERR_ANSWER_DOES_NOT_MATCH_PATTERN = "err.answer.does.not.match.pattern";
    private static final String MSG_TESTING_PASSED = "testing.passed";
    private static final String MSG_TESTING_FAILED = "testing.failed";
    private static final String MSG_ANSWERED_QUESTIONS_COUNT = "answered.questions.count";
    private static final String MSG_INPUT_CORRECT_ANSWER_NUMBER = "input.correct.answer.number";
    private static final String INPUT_CORRECT_ANSWER_NUMBERS = "input.correct.answer.numbers";

    private final MessageSourceWrapper messageSourceWrapper;
    private final DataStorageService dataStorageService;

    @Autowired
    public ConsoleTestsExecutorService(DataStorageService dataStorageService, MessageSourceWrapper messageSourceWrapper) {
        this.dataStorageService = dataStorageService;
        this.messageSourceWrapper = messageSourceWrapper;
    }

    @Override
    public void executeTest(long id) throws Exception {
        Test test = dataStorageService.getTest(id);
        Collections.shuffle(test.getQuestions());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int correctAnswers = 0;
            String name = greeting(reader, test.getName());
            for (Question question: test.getQuestions()) {
                Collections.shuffle(question.getAnswers());

                System.out.println(question.getText());
                for (int i = 0; i < question.getAnswers().size(); i++) {
                    QuestionAnswer answer = question.getAnswers().get(i);
                    System.out.println(String.format("%d) %s", i, answer.getText()));
                }

                String userAnswer = getUserAnswer(reader, question.isRadio());
                if (checkUserAnswer(userAnswer, question)) {
                    correctAnswers++;
                }
                System.out.println();
            }
            outputResults(name, correctAnswers, test);
        }
    }

    private String greeting(BufferedReader reader, String testName) throws IOException {
        System.out.println(messageSourceWrapper.getMsg(MSG_WELCOME_TO_TESTING, testName));

        System.out.println(messageSourceWrapper.getMsg(MSG_INPUT_YOUR_NAME));
        String name = reader.readLine();
        if (name.length() < 3) {
            System.err.println(messageSourceWrapper.getMsg(ERR_LOW_NAME_LEN));
            return greeting(reader, testName);
        }
        return name;
    }

    private String getUserAnswer(BufferedReader reader, boolean isRadioQuestion) throws IOException {
        System.out.println(messageSourceWrapper.getMsg(isRadioQuestion? MSG_INPUT_CORRECT_ANSWER_NUMBER: INPUT_CORRECT_ANSWER_NUMBERS));
        String userAnswer = reader.readLine();
        if (!userAnswer.matches("^(?:|(?:\\d+\\,{0,1}?)*\\d+)$")) {
            System.err.println(messageSourceWrapper.getMsg(ERR_ANSWER_DOES_NOT_MATCH_PATTERN));
            return getUserAnswer(reader, isRadioQuestion);
        }
        return userAnswer;

    }

    private boolean checkUserAnswer(String userAnswer, Question question) {
        String[] resArr = userAnswer.replaceAll(" ", "").split(",");
        if (resArr.length == 0) return false;

        int k = question.isRadio()? 1: resArr.length;

        boolean isCorrect = true;
        for (int i = 0; i < k; i++) {
            List<QuestionAnswer> answers = question.getAnswers();

            int index = Integer.parseInt(resArr[i]);
            isCorrect = isCorrect && (index < answers.size() && answers.get(index).isCorrect());
            if (!isCorrect) break;
        }
        return isCorrect;
    }

    private void outputResults(String name, int correctAnswers, Test test){
        System.out.println(messageSourceWrapper.getMsg((correctAnswers >= test.getCorrectQuestionsToPass())? MSG_TESTING_PASSED: MSG_TESTING_FAILED));
        System.out.println(messageSourceWrapper.getMsg(MSG_ANSWERED_QUESTIONS_COUNT, name, correctAnswers, test.getQuestions().size()));
    }
}
