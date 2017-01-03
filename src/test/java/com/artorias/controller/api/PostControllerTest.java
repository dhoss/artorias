package com.artorias.controller.api;

import com.artorias.WebApplication;
import com.artorias.controllers.api.PostController;
import com.artorias.database.jooq.tables.pojos.Author;
import com.artorias.dto.PostDTO;
import com.artorias.service.DefaultPostService;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.TransactionProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by devin on 12/21/16.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
@TestPropertySource(value="classpath:application.properties")
public class PostControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ConnectionProvider connectionProvider;

    @MockBean
    org.jooq.Configuration jooqConfiguration;

    @MockBean
    TransactionProvider transactionProvider;

    @MockBean
    DataSourceTransactionManager transactionManager;

    @MockBean
    DSLContext dsl;

    @MockBean
    private DefaultPostService postService;

    PostDTO postDto;

    @Before
    public void setup() {
        postDto = dto();
        this.mockMvc = standaloneSetup(new PostController(postService)).build();
    }

    @Test
    public void list_success() throws Exception {
        when(postService.pagedListAsDto(any(int.class))).thenReturn(Arrays.asList(postDto));
        this.mockMvc.perform(get("/api/posts").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

    }

    private Author expectedAuthor() {
        Timestamp t = ts();
        return new Author(1, "test author", "test@test.com", "1234", t, t, true, false);
    }

    private Timestamp ts() {
        return new Timestamp(1481136454);
    }

    // put this in a base class
    private PostDTO dto() {
        PostDTO expected = new PostDTO();
        expected.setPostId(1);
        expected.setTitle("test post");
        expected.setSlug("test-post");
        expected.setBody("test post body");
        expected.setAuthorName(expectedAuthor().getName());
        expected.setCreatedOn(ts());
        expected.setUpdatedOn(ts());
        expected.setPublishedOn(ts());
        return expected;
    }

}
