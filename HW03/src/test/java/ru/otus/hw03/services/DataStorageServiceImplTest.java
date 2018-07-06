package ru.otus.hw03.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.hw03.interfaces.dao.TestsDAO;
import ru.otus.hw03.interfaces.services.DataStorageService;

import static org.mockito.Mockito.when;
import static ru.otus.hw03.TestsConsts.MSG_UNEXPECTED_RESULT;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataStorageServiceImplTest {

    @MockBean
    TestsDAO testsDAO;

    @Test
    public void getTest() throws Exception {
        ru.otus.hw03.models.Test expectedTest = new ru.otus.hw03.models.Test(1L, "AnyTest", 1, null);
        when(testsDAO.getOne(1L)).thenReturn(expectedTest);
        when(testsDAO.getOne(2L)).thenReturn(null);
        DataStorageService dataStorageService = new DataStorageServiceImpl(testsDAO);

        String msg = String.format(MSG_UNEXPECTED_RESULT, "DataStorageServiceImplTest", "getTest");
        Assert.assertEquals(msg, expectedTest, dataStorageService.getTest(1L));
        Assert.assertNull(msg, dataStorageService.getTest(2L));
    }

}