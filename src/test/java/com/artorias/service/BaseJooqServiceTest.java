package com.artorias.service;

import static org.mockito.Mockito.*;

import org.jooq.Record;
import org.testng.annotations.*;


/**
 * Created by devin on 11/14/16.
 */

@Test(enabled=false)
public class BaseJooqServiceTest {

    private Record mockRecord;

    @BeforeClass
    public void setup() {
        mockRecord = mock(Record.class);
    }

    @AfterClass
    public void teardown() {}

}

