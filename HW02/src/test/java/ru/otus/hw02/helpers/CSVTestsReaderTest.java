package ru.otus.hw02.helpers;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.hw02.models.Question;
import ru.otus.hw02.models.QuestionAnswer;

import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

import static ru.otus.hw02.TestsConsts.MSG_UNEXPECTED_RESULT;

public class CSVTestsReaderTest {

    private static final String TEST_NAME_TEMPLATE = "Тест %d";
    private static final String QUESTION_TEXT_TEMPLATE = "Вопрос %d";
    public static final String ANSWER_TEXT_YES = "Да";
    public static final String ANSWER_TEXT_NO = "Нет";

    private  ru.otus.hw02.models.Test[] prepareExpectedTestsArr(){
        long testId = 1;
        long questionId = 1;
        long answerId = 1;

        ru.otus.hw02.models.Test[] tests = {new ru.otus.hw02.models.Test(testId++, String.format(TEST_NAME_TEMPLATE, 1), 1, new ArrayList<>()),
                new ru.otus.hw02.models.Test(testId, String.format(TEST_NAME_TEMPLATE, 2), 2, new ArrayList<>())};

        tests[0].getQuestions().add(new Question(questionId++, String.format(QUESTION_TEXT_TEMPLATE, 1), true,
                Arrays.asList(new QuestionAnswer(answerId++, ANSWER_TEXT_YES, true), new QuestionAnswer(answerId++, ANSWER_TEXT_NO, false))));

        tests[1].getQuestions().add(new Question(questionId++, String.format(QUESTION_TEXT_TEMPLATE, 1), false,
                Arrays.asList(new QuestionAnswer(answerId++, ANSWER_TEXT_YES, true), new QuestionAnswer(answerId++, ANSWER_TEXT_NO, false))));

        tests[1].getQuestions().add(new Question(questionId, String.format(QUESTION_TEXT_TEMPLATE, 2), true,
                Arrays.asList(new QuestionAnswer(answerId++, ANSWER_TEXT_YES, false), new QuestionAnswer(answerId, ANSWER_TEXT_NO, true))));
        return tests;
    }

    private String prepareCSVData(ru.otus.hw02.models.Test[] expectedTestsArr){
        List<String> csvTextList = new ArrayList<>();
        for (int i = 0; i < expectedTestsArr.length; i++) {
            csvTextList.add(String.format("t,0,%d,%s,%d", expectedTestsArr[i].getId(), expectedTestsArr[i].getName(), expectedTestsArr[i].getCorrectQuestionsToPass()));
            for (Question q: expectedTestsArr[i].getQuestions()) {
                csvTextList.add(String.format("q,%d,%d,%s,%s", expectedTestsArr[i].getId(), q.getId(), q.getText(), q.isRadio()));
                for (QuestionAnswer a: q.getAnswers()) {
                    csvTextList.add(String.format("a,%d,%d,%s,%s", q.getId(), a.getId(), a.getText(), a.isCorrect()));
                }
            }
        }

        Collections.shuffle(csvTextList);
        csvTextList.add(0, "ROW_TYPE,PARENT_ID,ID,TEXT,EXT_INFO");

        String csvText = csvTextList.stream().collect(Collectors.joining("\n"));
        return csvText;
    }

    @Test
    public void readTests() throws Exception {
        ru.otus.hw02.models.Test[] expectedTestsArr = prepareExpectedTestsArr();
        Map<Long, ru.otus.hw02.models.Test> expectedTests = new HashMap<>();
        expectedTests.put(expectedTestsArr[0].getId(), expectedTestsArr[0]);
        expectedTests.put(expectedTestsArr[1].getId(), expectedTestsArr[1]);

        Map<Long, ru.otus.hw02.models.Test> actualTests = new HashMap<>();

        StringReader reader = new StringReader(prepareCSVData(expectedTestsArr));
        CSVTestsReader.readTests(reader, actualTests);

        Assert.assertEquals(String.format(MSG_UNEXPECTED_RESULT, "CSVTestsReaderTest", "readTests"), expectedTests, actualTests);
    }

}