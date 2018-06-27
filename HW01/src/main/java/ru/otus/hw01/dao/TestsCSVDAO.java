package ru.otus.hw01.dao;

import ru.otus.hw01.helpers.CSVTestsReader;
import ru.otus.hw01.interfaces.dao.ITestsDAO;
import ru.otus.hw01.models.Test;

import java.io.FileReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestsCSVDAO implements ITestsDAO {

    private final String CSV_FILE_LOCATION;
    private final Map<Long, Test> tests;


    public TestsCSVDAO(String csvFileLocation) {
        this.CSV_FILE_LOCATION = csvFileLocation;
        tests = new ConcurrentHashMap<>();
    }

    @Override
    public Test getOne(long id) throws Exception {
        if (tests.size() == 0) {
            CSVTestsReader.readTests(new FileReader(CSV_FILE_LOCATION), tests);
        }
        return tests.get(id);
    }
}
