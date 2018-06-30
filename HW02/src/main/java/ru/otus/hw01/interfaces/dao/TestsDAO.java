package ru.otus.hw01.interfaces.dao;

import ru.otus.hw01.models.Test;

public interface TestsDAO {
    Test getOne(long id) throws Exception;
}
