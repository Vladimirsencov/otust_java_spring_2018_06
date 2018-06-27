package ru.otus.hw01.services;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.hw01.interfaces.dao.ITestsDAO;
import ru.otus.hw01.interfaces.services.IDataStorageService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.otus.hw01.TestsConsts.MSG_UNEXPECTED_RESULT;

public class DataStorageServiceTest {

    @Test
    public void getTest() throws Exception {
        ITestsDAO testsDAO = mock(ITestsDAO.class);
        ru.otus.hw01.models.Test expectedTest = new ru.otus.hw01.models.Test(1L, "AnyTest", 1, null);
        when(testsDAO.getOne(1L)).thenReturn(expectedTest);
        when(testsDAO.getOne(2L)).thenReturn(null);
        IDataStorageService dataStorageService = new DataStorageService(testsDAO);

        String msg = String.format(MSG_UNEXPECTED_RESULT, "DataStorageServiceTest", "getTest");
        Assert.assertEquals(msg, expectedTest, dataStorageService.getTest(1L));
        Assert.assertNull(msg, dataStorageService.getTest(2L));
    }

}