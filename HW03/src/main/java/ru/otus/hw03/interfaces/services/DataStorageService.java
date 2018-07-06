package ru.otus.hw03.interfaces.services;

import ru.otus.hw03.models.Test;

public interface DataStorageService {
    Test getTest(long id) throws Exception;
}
