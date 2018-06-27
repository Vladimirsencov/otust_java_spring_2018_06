package ru.otus.hw01.services;

import ru.otus.hw01.interfaces.dao.ITestsDAO;
import ru.otus.hw01.interfaces.services.IDataStorageService;
import ru.otus.hw01.models.Test;

public class DataStorageService implements IDataStorageService {
    private final ITestsDAO testsDAO;

    public DataStorageService(ITestsDAO testsDAO) {
        this.testsDAO = testsDAO;
    }

    @Override
    public Test getTest(long id) throws Exception {
        return testsDAO.getOne(id);
    }
}
