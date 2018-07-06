package ru.otus.hw02.interfaces.services;

import ru.otus.hw02.models.Test;

public interface DataStorageService {
    Test getTest(long id) throws Exception;
}
