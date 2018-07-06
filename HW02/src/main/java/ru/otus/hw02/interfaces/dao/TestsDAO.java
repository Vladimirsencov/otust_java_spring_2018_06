package ru.otus.hw02.interfaces.dao;

import ru.otus.hw02.models.Test;

public interface TestsDAO {
    Test getOne(long id) throws Exception;
}
