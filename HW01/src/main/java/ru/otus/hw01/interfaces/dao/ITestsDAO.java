package ru.otus.hw01.interfaces.dao;

import ru.otus.hw01.models.Test;

public interface ITestsDAO {
    Test getOne(long id) throws Exception;
}
