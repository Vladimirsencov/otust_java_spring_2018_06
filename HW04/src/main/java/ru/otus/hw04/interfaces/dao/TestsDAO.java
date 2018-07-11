package ru.otus.hw04.interfaces.dao;

import ru.otus.hw04.models.Test;

public interface TestsDAO {
    Test getOne(long id) throws Exception;
}
