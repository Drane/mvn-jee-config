package com.prefabsoft.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by jochen on 05/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class FakeServiceTest extends BaseServiceTest {

    FakeService fakeService;
    @Mock FakeDao fakeDao;
    @Mock Logger mockLogger;

    @Before
    public void setUp() throws Exception {
        fakeService = new FakeService(fakeDao);
        fakeService.logger = mockLogger; //LoggerFactory.getLogger(FakeService.class);
        processConfigAnnotations(fakeService);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetRoot() throws Exception {
        when(fakeDao.findAll()).thenReturn(Arrays.asList("X", "Y", "Z"));
        String response = fakeService.getRoot();
        logger.warn("response: {}", response);
        assertEquals("serverAddress:test-rest fakeDaoSTuff: [X, Y, Z]", response);
    }
}