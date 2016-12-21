package com.artorias.controller.api;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by devin on 12/21/16.
 */

@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application-test.properties")
public class PostControllerTest {
    @Autowired
    MockMvc mockMvc;


    @BeforeClass
    public void setup() {
    }

    @AfterClass
    public void teardown() {
    }

    @Test
    public void list_success() {

    }

}
