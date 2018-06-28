package ru.otus.hw01.services;

import ru.otus.hw01.interfaces.dao.TestsDAO;
import ru.otus.hw01.interfaces.services.DataStorageService;
import ru.otus.hw01.models.Test;

public class DataStorageServiceImpl implements DataStorageService {
    private final TestsDAO testsDAO;

    public DataStorageServiceImpl(TestsDAO testsDAO) {
        this.testsDAO = testsDAO;
    }

    @Override
    public Test getTest(long id) throws Exception {
        return testsDAO.getOne(id);
    }
}
