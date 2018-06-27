package ru.otus.hw01.interfaces.services;

import ru.otus.hw01.models.Test;

public interface IDataStorageService {
    Test getTest(long id) throws Exception;
}
