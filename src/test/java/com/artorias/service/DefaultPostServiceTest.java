package com.artorias.service;

import com.artorias.TestDataProvider;
import com.artorias.database.jooq.tables.pojos.Post;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Table;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.MockitoAnnotations.initMocks;


/**
 * Created by devin on 11/14/16.
 */
@Slf4j
@Test
public class DefaultPostServiceTest {

    @Mock
    private ModelMapper mockModelMapper;

    // Initialise your data provider (implementation further down):
    MockDataProvider provider;
    MockConnection connection;

    // Pass the mock connection to a jOOQ DSLContext:
    DSLContext create;

    //@InjectMocks
    DefaultPostService postService;

    Post expectedPost;

    Timestamp expectedDate;

    String searchPostSlug;

    @BeforeMethod
    public void setup() {
        initMocks(this);
        provider = new TestDataProvider();
        connection = new MockConnection(provider);
        create = DSL.using(connection, SQLDialect.POSTGRES_9_5);
        postService = new DefaultPostService(create, mockModelMapper);
        expectedDate = new Timestamp(1481136454);
        expectedPost = new Post(1, "Test Post", "test-post", "This is a test post", 1, expectedDate, expectedDate, expectedDate);
        searchPostSlug = "test-post";

    }

    @Test
    public void find() {
        Assert.assertTrue(check(expectedPost, findPost()));
    }

    @Test
    public void add() {
        postService.add(expectedPost);
    }

    @Test
    public void delete() {
        Post p = findPost();
        postService.delete(p);
    }

    @Test
    public void update() {
        Post p = findPost();
        p.setTitle("fart");
        postService.update(p);
    }

    private boolean check(Post expected, Post received) {
        return expected.getPostId() == received.getPostId();
    }

    private Post findPost() {
        return postService.find(searchPostSlug);
    }

}

