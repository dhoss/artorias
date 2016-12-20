package com.artorias.service;

import com.artorias.util.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.DSLContext;
import org.jooq.UpdatableRecord;
import org.jooq.impl.DSL;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by devin on 11/13/16.
 */

@Slf4j
@Service
public abstract class BaseJooqService<R extends Record, T extends Table<R>, E, DT> {
    protected int pageSize;

    private Class<E> recordClass;

    @Autowired
    protected DSLContext dsl;

    @Autowired
    protected ModelMapper mapper;

    public BaseJooqService() {
        setDefaults();
    }

    public BaseJooqService(DSLContext d, ModelMapper m) {
        setDefaults();
        this.dsl = d;
        this.mapper = m;
    }

    protected int getPage(String pageParam) {
        int page = 1;
        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
        }

        return page;
    }

    // CREATE /////////
    public void add(E record) {
        storeRecord(record);
    }

    private void storeRecord(E record) {
        UpdatableRecord r = (UpdatableRecord) buildRecord(record);
        r.store();
    }
    ///////////////////

    // READ ///////////
    public List<E> list(int pageNumber) {
        Pagination pager = new Pagination(count());
        return dsl.select()
                .from(table())
                .orderBy(DSL.field("UPDATED_ON").desc(), DSL.field("CREATED_ON").desc())
                .limit(pageSize)
                .offset(pager.offsetFromPage(pageNumber))
                .fetch()
                .into(this.recordClass);
    }
    ////////////////////

    // UPDATE //////////
    public void update(E record) {
        storeRecord(record);
    }
    ////////////////////

    // DELETE //////////
    public void delete(E record) {
        UpdatableRecord r = (UpdatableRecord) buildRecord(record);
        r.delete();
    }
    ////////////////////

    public Integer count() {
        return (Integer) dsl.selectCount()
                .from(table())
                .fetchOne().getValue(0);
    }

    // to be implemented by the inheriting class

    protected abstract T table();

    protected abstract R buildRecord(E e);

    public abstract E find(String n);

    public abstract DT singleAsDto(E p);

    public abstract List<DT> listAsDto(List<E> p);

    private void setDefaults() {
        this.recordClass = (Class<E>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.pageSize = 50;
    }
}
