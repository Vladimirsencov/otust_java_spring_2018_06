package ru.otus.hw01.helpers;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ru.otus.hw01.models.Question;
import ru.otus.hw01.models.QuestionAnswer;
import ru.otus.hw01.models.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSV2ModelsTransformHelper {

    private static final String COLUMN_ROW_TYPE = "ROW_TYPE";
    private static final String COLUMN_PARENT_ID = "PARENT_ID";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_TEXT = "TEXT";
    private static final String COLUMN_EXT_INFO = "EXT_INFO";

    private static final String ROW_TYPE_TEST = "t";
    private static final String ROW_TYPE_QUESTION = "q";
    private static final String ROW_TYPE_QUESTION_ANSWER = "a";



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
            int o1RowTypeIndex = o1.extInfo.equalsIgnoreCase(ROW_TYPE_TEST)? 0: (o1.extInfo.equalsIgnoreCase(ROW_TYPE_QUESTION)? 1: 0);
            int o2RowTypeIndex = o2.extInfo.equalsIgnoreCase(ROW_TYPE_TEST)? 0: (o2.extInfo.equalsIgnoreCase(ROW_TYPE_QUESTION)? 1: 0);
            int res = Integer.compare(o1RowTypeIndex, o2RowTypeIndex);
            return  res == 1? res: Long.compare(o1.parentId, o2.parentId);
        });
    }

    private static List<CSVRecordDTO> csvFile2DTOList(String csvFileName) throws IOException{
        List<CSVRecordDTO> dtoList = new ArrayList<>();
        try (CSVParser parser = new CSVParser(new FileReader(csvFileName), CSVFormat.DEFAULT.withHeader())) {
            for (CSVRecord r : parser) {
                dtoList.add(new CSVRecordDTO(r.get(COLUMN_ROW_TYPE), Long.parseLong(r.get(COLUMN_PARENT_ID)), Long.parseLong(r.get(COLUMN_ID)), r.get(COLUMN_TEXT), r.get(COLUMN_EXT_INFO)));
            }
        }
        return dtoList;
    }

    public static void csvFile2Tests(String csvFileName, Map<Long, Test> tests) throws IOException {
        List<CSVRecordDTO> dtoList = csvFile2DTOList(csvFileName);
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
