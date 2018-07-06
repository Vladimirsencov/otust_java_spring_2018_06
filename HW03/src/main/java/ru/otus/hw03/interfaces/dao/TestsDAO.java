package ru.otus.hw03.interfaces.dao;

import ru.otus.hw03.models.Test;

public interface TestsDAO {
    Test getOne(long id) throws Exception;
}
