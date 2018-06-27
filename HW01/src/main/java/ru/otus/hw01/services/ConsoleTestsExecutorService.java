package ru.otus.hw01.services;

import ru.otus.hw01.interfaces.services.IDataStorageService;
import ru.otus.hw01.interfaces.services.ITestsExecutorService;
import ru.otus.hw01.models.Question;
import ru.otus.hw01.models.QuestionAnswer;
import ru.otus.hw01.models.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class ConsoleTestsExecutorService implements ITestsExecutorService {
    private final IDataStorageService dataStorageService;

    public ConsoleTestsExecutorService(IDataStorageService dataStorageService) {
        this.dataStorageService = dataStorageService;
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
        System.out.println(String.format("Добро пожаловать на прохождение теста %s", testName));
        System.out.println("Введите свое имя...");
        String name = reader.readLine();
        if (name.length() < 3) {
            System.err.println("Длина имени меньше 3 символов");
            return greeting(reader, testName);
        }
        return name;
    }

    private String getUserAnswer(BufferedReader reader, boolean isRadioQuestion) throws IOException {
        System.out.println("Введите " + (isRadioQuestion? "номер правильного варианта ответа":  "номера правильных вариантов ответов через запятую"));
        String userAnswer = reader.readLine();
        if (!userAnswer.matches("^(?:|(?:\\d+\\,{0,1}?)*\\d+)$")) {
            System.err.println("Ответ не соответствует шаблону!");
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
        if (correctAnswers >= test.getCorrectQuestionsToPass()) {
            System.out.println("Поздравлем! Тест сдан!");
        } else {
            System.out.println("Соболезнуем! Тест не сдан!");
        }
        System.out.println(String.format("%s Вы ответили на %d/%d вопросов", name, correctAnswers, test.getQuestions().size()));
    }
}
