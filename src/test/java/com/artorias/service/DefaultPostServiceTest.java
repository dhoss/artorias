package com.artorias.service;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * Created by devin on 11/14/16.
 */

@Test(enabled=false)
public class DefaultPostServiceTest {

    @Mock
    private DSLContext mockDSLContext;

    @Mock
    private ModelMapper mockModelMapper;

    @InjectMocks
    DefaultPostService postService;

    @BeforeMethod
    public void setup() {
       initMocks(this);
    }

    @Test
    public void find(){}
}

