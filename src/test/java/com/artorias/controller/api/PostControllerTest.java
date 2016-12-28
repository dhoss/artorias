package com.artorias.controller.api;

import com.artorias.controllers.api.PostController;
import com.artorias.service.DefaultPostService;
import org.jooq.DSLContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by devin on 12/21/16.
 */

@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application-test.properties")
@WebMvcTest(PostController.class)
public class PostControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private DSLContext dsl;

    @Autowired
    private DefaultPostService postService;

    @BeforeClass
    public void setup() {

    }

    @AfterClass
    public void teardown() {
    }

    @Test
    public void list_success() throws Exception {
        this.mockMvc.perform(get("/api/posts").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));

    }

}
