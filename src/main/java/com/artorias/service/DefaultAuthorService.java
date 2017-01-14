package com.artorias.service;

import com.artorias.database.jooq.tables.Author;
import com.artorias.database.jooq.tables.records.AuthorRecord;
import com.artorias.dto.PostDTO;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jvnet.hk2.annotations.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import static com.artorias.database.jooq.tables.Author.AUTHOR;

/**
 * Created by devin on 11/13/16.
 */

@Slf4j
@Service
public class DefaultAuthorService extends BaseJooqService<AuthorRecord, Author, com.artorias.database.jooq.tables.pojos.Author, PostDTO> {

    @Autowired
    private DSLContext dsl;

    public DefaultAuthorService(DSLContext d, ModelMapper map) {
        this.mapper = map;
        this.dsl = d;
    }

    @Override
    public com.artorias.database.jooq.tables.pojos.Author find(String id) {
        return this.dsl.select()
                .from(AUTHOR)
                .where(AUTHOR.NAME.equal(id))
                .fetchAny()
                .into(com.artorias.database.jooq.tables.pojos.Author.class);
    }

    // fix this to allow for more flexible queries instead of overriding the whole thing
    /*@Override
    public List<PostDTO> list(int pageNumber) {
        Pagination pager = new Pagination(count());
        Timestamp ts = new Timestamp(1481136454);

       return dsl.select(AUTHOR.AUTHOR_ID, AUTHOR.TITLE, AUTHOR.SLUG, AUTHOR.BODY, AUTHOR.AUTHOR_ID, AUTHOR.CREATED_ON, AUTHOR.UPDATED_ON, AUTHOR.PUBLISHED_ON, AUTHOR.NAME.as("AUTHOR_NAME"))
                .from(AUTHOR, AUTHOR)
                .orderBy(AUTHOR.UPDATED_ON.desc(), AUTHOR.CREATED_ON.desc())
                .limit(pageSize)
                .offset(pager.offsetFromPage(pageNumber))
                .fetchInto(PostDTO.class);
    }*/
/*
    public PostDTO findWithRelated(String slug) {
        return this.dsl.select(AUTHOR.AUTHOR_ID, AUTHOR.TITLE, AUTHOR.SLUG, AUTHOR.BODY, AUTHOR.AUTHOR_ID, AUTHOR.CREATED_ON, AUTHOR.UPDATED_ON, AUTHOR.PUBLISHED_ON, AUTHOR.NAME.as("AUTHOR_NAME"))
                .from(AUTHOR, AUTHOR)
                .join(AUTHOR)
                .on(AUTHOR.AUTHOR_ID.equal(AUTHOR.AUTHOR_ID))
                .where(AUTHOR.SLUG.equal(slug))
                .fetchOneInto(PostDTO.class);
    }*/

    @Override
    public AuthorRecord buildRecord(com.artorias.database.jooq.tables.pojos.Author author) {
        AuthorRecord p = dsl.fetchOne(AUTHOR, AUTHOR.AUTHOR_ID.equal(author.getAuthorId()));
        if (p == null) {
            p = mapper.map(author, AuthorRecord.class);
        }
        return p;
    }

    @Override
    protected com.artorias.database.jooq.tables.Author table() {
        return AUTHOR;
    }

    // this has to be defined here or DefaultPostService tests fail with a NPE for some reason
    @Override
    public int count() {
        Integer count =  (Integer) dsl.selectCount()
                .from(table())
                .fetchOne(0, int.class);
        return count;
    }
}
