package ru.otus.hw03.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ru.otus.hw03.models.Question;
import ru.otus.hw03.models.QuestionAnswer;
import ru.otus.hw03.models.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVTestsReader {

    private static final String COLUMN_ROW_TYPE = "ROW_TYPE";
    private static final String COLUMN_PARENT_ID = "PARENT_ID";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_TEXT = "TEXT";
    private static final String COLUMN_EXT_INFO = "EXT_INFO";

    private static final String ROW_TYPE_TEST = "t";
    private static final String ROW_TYPE_QUESTION = "q";
    private static final String ROW_TYPE_QUESTION_ANSWER = "a";

    @Data
    @AllArgsConstructor
    private static class CSVRecordDTO {
        private String rowType;
        private long parentId;
        private long id;
        private String text;
        private String extInfo;
    }

    private static void sortDTOList(List<CSVRecordDTO> dtoList) {
        dtoList.sort((o1, o2) -> {
            int o1RowTypeIndex = o1.rowType.equalsIgnoreCase(ROW_TYPE_TEST)? 0: (o1.rowType.equalsIgnoreCase(ROW_TYPE_QUESTION)? 1: 2);
            int o2RowTypeIndex = o2.rowType.equalsIgnoreCase(ROW_TYPE_TEST)? 0: (o2.rowType.equalsIgnoreCase(ROW_TYPE_QUESTION)? 1: 2);
            int res = Integer.compare(o1RowTypeIndex, o2RowTypeIndex);
            res = res == 0? Long.compare(o1.parentId, o2.parentId): res;
            res = res == 0? Long.compare(o1.id, o2.id): res;

            return res;
        });
    }

    private static List<CSVRecordDTO> readDTOList(Reader reader) throws IOException{
        List<CSVRecordDTO> dtoList = new ArrayList<>();
        try (CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {
            for (CSVRecord r : parser) {
                dtoList.add(new CSVRecordDTO(r.get(COLUMN_ROW_TYPE), Long.parseLong(r.get(COLUMN_PARENT_ID)), Long.parseLong(r.get(COLUMN_ID)), r.get(COLUMN_TEXT), r.get(COLUMN_EXT_INFO)));
            }
        }
        return dtoList;
    }

    public static void readTests(Reader reader, Map<Long, Test> tests) throws IOException {
        List<CSVRecordDTO> dtoList = readDTOList(reader);
        sortDTOList(dtoList);

        Map<Long, Question> questions = new HashMap<>();

        for (CSVRecordDTO dto: dtoList) {
            if (dto.rowType.equalsIgnoreCase(ROW_TYPE_TEST)) {
                Test test = new Test(dto.id, dto.text, Integer.parseInt(dto.extInfo), new ArrayList<>());
                tests.put(test.getId(), test);
            } else if (dto.rowType.equalsIgnoreCase(ROW_TYPE_QUESTION)) {
                Test test = tests.get(dto.parentId);
                Question question = new Question(dto.id, dto.text, Boolean.parseBoolean(dto.extInfo), new ArrayList<>());
                questions.put(question.getId(), question);
                test.getQuestions().add(question);
            } else if (dto.rowType.equalsIgnoreCase(ROW_TYPE_QUESTION_ANSWER)) {
                Question question = questions.get(dto.parentId);
                QuestionAnswer answer = new QuestionAnswer(dto.id, dto.text, Boolean.parseBoolean(dto.extInfo));
                question.getAnswers().add(answer);
            }
        }
    }
}
