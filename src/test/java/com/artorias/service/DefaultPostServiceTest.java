package com.artorias.service;

import com.artorias.TestDataProvider;
import com.artorias.database.jooq.tables.pojos.Post;
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

import java.sql.Timestamp;

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

    @BeforeMethod
    public void setup() {
        initMocks(this);
        provider = new TestDataProvider();
        connection = new MockConnection(provider);
        create = DSL.using(connection, SQLDialect.POSTGRES_9_5);
        postService = new DefaultPostService(create, mockModelMapper);

    }

    @Test
    public void find() {
        Post expected = new Post(1, "Test Post", "test-post", "This is a test post", 1, new Timestamp(1481136454), new Timestamp(1481136454), new Timestamp(1481136454));
        Post received = postService.find("test-post");
        Assert.assertEquals(expected, received);
    }
}

