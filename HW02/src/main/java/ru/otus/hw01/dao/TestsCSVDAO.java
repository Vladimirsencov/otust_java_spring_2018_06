package ru.otus.hw01.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.hw01.helpers.CSVTestsReader;
import ru.otus.hw01.interfaces.dao.TestsDAO;
import ru.otus.hw01.interfaces.i18n.MessageSourceWrapper;
import ru.otus.hw01.models.Test;

import java.io.FileReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository("testsDAO")
public class TestsCSVDAO implements TestsDAO {

    public static final String PROPERTY_TEST_FILE_LOCALE_SUFFIX = "test.file.locale.suffix";
    public static final String PROPERTY_CSV_FILE_LOCATION_TEMPLATE = "csv.file.location.template";

    private final String csvFileLocation;
    private final Map<Long, Test> tests;

    public TestsCSVDAO(@Autowired MessageSourceWrapper messageSourceWrapper, @Value("${" + PROPERTY_CSV_FILE_LOCATION_TEMPLATE + "}") String csvFileLocation) {
        String suffix = messageSourceWrapper.getMsg(PROPERTY_TEST_FILE_LOCALE_SUFFIX);
        this.csvFileLocation = String.format(csvFileLocation, suffix);
        tests = new ConcurrentHashMap<>();
    }

    @Override
    public Test getOne(long id) throws Exception {
        if (tests.size() == 0) {
            CSVTestsReader.readTests(new FileReader(csvFileLocation), tests);
        }
        return tests.get(id);
    }
}
