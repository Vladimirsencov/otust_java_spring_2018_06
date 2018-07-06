package ru.otus.hw03.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.hw03.interfaces.dao.TestsDAO;
import ru.otus.hw03.interfaces.services.DataStorageService;
import ru.otus.hw03.models.Test;

@Service("dataStorageService")
public class DataStorageServiceImpl implements DataStorageService {

    private final TestsDAO testsDAO;

    @Autowired
    public DataStorageServiceImpl(TestsDAO testsDAO) {
        this.testsDAO = testsDAO;
    }

    @Override
    public Test getTest(long id) throws Exception {
        return testsDAO.getOne(id);
    }
}
