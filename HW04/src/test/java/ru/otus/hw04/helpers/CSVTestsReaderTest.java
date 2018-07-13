package ru.otus.hw04.helpers;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.hw04.models.Question;
import ru.otus.hw04.models.QuestionAnswer;

import java.io.*;
import java.util.*;

import static ru.otus.hw04.TestsConsts.MSG_UNEXPECTED_RESULT;

public class CSVTestsReaderTest {

    private static final String TEST_NAME_TEMPLATE = "Тест %d";
    private static final String QUESTION_TEXT_TEMPLATE = "Вопрос %d";
    public static final String ANSWER_TEXT_YES = "Да";
    public static final String ANSWER_TEXT_NO = "Нет";

    private  ru.otus.hw04.models.Test[] prepareExpectedTestsArr(){
        long testId = 1;
        long questionId = 1;
        long answerId = 1;

        ru.otus.hw04.models.Test[] tests = {new ru.otus.hw04.models.Test(testId++, String.format(TEST_NAME_TEMPLATE, 1), 1, new ArrayList<>()),
                new ru.otus.hw04.models.Test(testId, String.format(TEST_NAME_TEMPLATE, 2), 2, new ArrayList<>())};

        tests[0].getQuestions().add(new Question(questionId++, String.format(QUESTION_TEXT_TEMPLATE, 1), true,
                Arrays.asList(new QuestionAnswer(answerId++, ANSWER_TEXT_YES, true), new QuestionAnswer(answerId++, ANSWER_TEXT_NO, false))));

        tests[1].getQuestions().add(new Question(questionId++, String.format(QUESTION_TEXT_TEMPLATE, 1), false,
                Arrays.asList(new QuestionAnswer(answerId++, ANSWER_TEXT_YES, true), new QuestionAnswer(answerId++, ANSWER_TEXT_NO, false))));

        tests[1].getQuestions().add(new Question(questionId, String.format(QUESTION_TEXT_TEMPLATE, 2), true,
                Arrays.asList(new QuestionAnswer(answerId++, ANSWER_TEXT_YES, false), new QuestionAnswer(answerId, ANSWER_TEXT_NO, true))));
        return tests;
    }

    @Test
    public void readTests() throws Exception {
        ru.otus.hw04.models.Test[] expectedTestsArr = prepareExpectedTestsArr();
        Map<Long, ru.otus.hw04.models.Test> expectedTests = new HashMap<>();
        expectedTests.put(expectedTestsArr[0].getId(), expectedTestsArr[0]);
        expectedTests.put(expectedTestsArr[1].getId(), expectedTestsArr[1]);

        Map<Long, ru.otus.hw04.models.Test> actualTests = new HashMap<>();

        File testFile = new File(CSVTestsReaderTest.class.getClassLoader().getResource("test.csv").toURI());
        CSVTestsReader.readTests(new FileReader(testFile), actualTests);

        Assert.assertEquals(String.format(MSG_UNEXPECTED_RESULT, "CSVTestsReaderTest", "readTests"), expectedTests, actualTests);
    }

}