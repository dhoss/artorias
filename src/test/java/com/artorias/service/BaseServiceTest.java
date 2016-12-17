package com.artorias.service;

import com.artorias.TestDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;

import static org.mockito.MockitoAnnotations.initMocks;


/**
 * Created by devin on 11/14/16.
 */
@Slf4j
@Test
public abstract class BaseServiceTest<E, P> {

    @Mock
    protected ModelMapper mockModelMapper;

    // Initialise your data provider (implementation further down):
    protected MockDataProvider provider;
    protected MockConnection connection;

    // Pass the mock connection to a jOOQ DSLContext:
    protected DSLContext create;

    protected E service;

    private P expectedRecord;

    String searchTerm;

    @BeforeMethod
    public void setup() {
        initMocks(this);
        provider = new TestDataProvider();
        connection = new MockConnection(provider);
        create = DSL.using(connection, SQLDialect.POSTGRES_9_5);
        service = setupService();//Class<E>;//(Class<E>) ((ParameterizedType) getClass()
                //.getGenericSuperclass()).getActualTypeArguments()[0];
        expectedRecord = expectedRecord();
        searchTerm = searchTerm();

    }

    @Test
    public void find() {
        Assert.assertTrue(check(expectedRecord, findRecord()));
    }

    @Test
    public void add() {
        addRecord(expectedRecord, service);
    }

    @Test
    public void delete() {
        P p = findRecord();
        deleteRecord(p, service);
    }

    @Test
    public void update() {
        P p = findRecord();
        updateRecord(p, service,"test update");
    }

    private boolean check(P expected, P received) {
        return getExpectedId(expected) == getActualId(received);
    }

    private P findRecord() {
        return findRecord(searchTerm, service);
    }

    protected abstract P expectedRecord();

    protected abstract String searchTerm();

    protected abstract void updateRecord(P record, E service,  String newData);

    protected abstract void deleteRecord(P record, E service);

    protected abstract void addRecord(P record, E service);

    protected abstract P findRecord(String searchString, E service);

    protected abstract int getExpectedId(P record);

    protected abstract int getActualId(P record);

    protected abstract E setupService();

}

