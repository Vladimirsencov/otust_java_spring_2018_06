package ru.otus.hw04.interfaces.services;

import ru.otus.hw04.models.Test;

public interface DataStorageService {
    Test getTest(long id) throws Exception;
}
