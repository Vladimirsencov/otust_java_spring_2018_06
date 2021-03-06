package ru.otus.hw04.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.Shell;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.hw04.interfaces.dao.TestsDAO;
import ru.otus.hw04.interfaces.services.DataStorageService;

import static org.mockito.Mockito.when;
import static ru.otus.hw04.TestsConsts.MSG_UNEXPECTED_RESULT;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataStorageServiceImplTest {

    @Configuration
    static class TestConfiguration {
        @Bean
        public DataStorageService dataStorageService(@Autowired  TestsDAO testsDAO) {
            return new DataStorageServiceImpl(testsDAO);
        }
    }

    @MockBean
    TestsDAO testsDAO;

    @Autowired
    DataStorageService dataStorageService;

    @Test
    public void getTest() throws Exception {

        ru.otus.hw04.models.Test expectedTest = new ru.otus.hw04.models.Test(1L, "AnyTest", 1, null);
        when(testsDAO.getOne(1L)).thenReturn(expectedTest);
        when(testsDAO.getOne(2L)).thenReturn(null);

        String msg = String.format(MSG_UNEXPECTED_RESULT, "DataStorageServiceImplTest", "getTest");
        Assert.assertEquals(msg, expectedTest, dataStorageService.getTest(1L));
        Assert.assertNull(msg, dataStorageService.getTest(2L));
    }

}