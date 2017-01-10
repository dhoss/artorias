package com.artorias.service;

import com.artorias.database.jooq.tables.Author;
import com.artorias.database.jooq.tables.Post;
import com.artorias.database.jooq.tables.records.AuthorRecord;
import com.artorias.database.jooq.tables.records.PostRecord;
import com.artorias.dto.PostDTO;
import com.artorias.util.Pagination;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jvnet.hk2.annotations.Service;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.artorias.database.jooq.tables.Author.AUTHOR;
import static com.artorias.database.jooq.tables.Post.POST;

/**
 * Created by devin on 11/13/16.
 */

@Slf4j
@Service
public class DefaultPostService extends BaseJooqService<PostRecord, com.artorias.database.jooq.tables.Post, com.artorias.database.jooq.tables.pojos.Post, PostDTO> {

    /*@Autowired
    private ModelMapper mapper;*/

    @Autowired
    private DSLContext dsl;

    public DefaultPostService(DSLContext d, ModelMapper map) {
        this.mapper = map;
        this.dsl = d;
    }

    @Override
    public com.artorias.database.jooq.tables.pojos.Post find(String slug) {
        return this.dsl.select()
                .from(POST)
                .where(POST.SLUG.equal(slug))
                .fetchAny()
                .into(com.artorias.database.jooq.tables.pojos.Post.class);
    }

    // fix this to allow for more flexible queries instead of overriding the whole thing
    @Override
    public List<PostDTO> list(int pageNumber) {
        Pagination pager = new Pagination(count());
        Timestamp ts = new Timestamp(1481136454);

       return dsl.select(POST.POST_ID, POST.TITLE, POST.SLUG, POST.BODY, POST.AUTHOR_ID, POST.CREATED_ON, POST.UPDATED_ON, POST.PUBLISHED_ON, AUTHOR.NAME.as("AUTHOR_NAME"))
                .from(POST, AUTHOR)
                .orderBy(POST.UPDATED_ON.desc(), POST.CREATED_ON.desc())
                .limit(pageSize)
                .offset(pager.offsetFromPage(pageNumber))
                .fetchInto(PostDTO.class);
    }

    public PostDTO findWithRelated(String slug) {
        Author a = AUTHOR.as("a");
        Post p = POST.as("p");

        SelectConditionStep<Record9<Integer, String, String, String, Integer, Timestamp, Timestamp, Timestamp, String>> sql = this.dsl.select(p.POST_ID, p.TITLE, p.SLUG, p.BODY, p.AUTHOR_ID, p.CREATED_ON, p.UPDATED_ON, p.PUBLISHED_ON, a.NAME)
                .from(p, a)
                .join(a)
                .on(p.AUTHOR_ID.equal(a.AUTHOR_ID))
                .where(p.SLUG.equal(slug));
        PostRecord post = sql.fetchOneInto(POST);
        AuthorRecord author = sql.fetchOneInto(AUTHOR);
        return dto(post, author);
    }

    @Override
    public PostRecord buildRecord(com.artorias.database.jooq.tables.pojos.Post post) {
        PostRecord p = dsl.fetchOne(POST, POST.POST_ID.equal(post.getPostId()));
        if (p == null) {
            p = mapper.map(post, PostRecord.class);
        }
        return p;
    }

    @Override
    protected com.artorias.database.jooq.tables.Post table() {
        return POST;
    }

    // put this in a base class
    private PostDTO dto(PostRecord p, AuthorRecord a) {
        PostDTO dto = new PostDTO();
        dto.setPostId(p.getValue(POST.POST_ID));
        dto.setTitle(p.getValue(POST.TITLE));
        dto.setSlug(p.getValue(POST.SLUG));
        dto.setBody(p.getValue(POST.BODY));
        dto.setAuthorName(a.getValue(AUTHOR.NAME));
        dto.setAuthorId(p.getValue(POST.AUTHOR_ID));
        dto.setCreatedOn(p.getValue(POST.CREATED_ON));
        dto.setUpdatedOn(p.getValue(POST.UPDATED_ON));
        dto.setPublishedOn(p.getValue(POST.PUBLISHED_ON));
        return dto;
    }

    private com.artorias.database.jooq.tables.pojos.Author buildAuthor(AuthorRecord author) {
        return new com.artorias.database.jooq.tables.pojos.Author(
                author.getValue(AUTHOR.AUTHOR_ID),
                author.getValue(AUTHOR.NAME),
                author.getValue(AUTHOR.EMAIL),
                author.getValue(AUTHOR.PASSWORD),
                author.getValue(AUTHOR.CREATED_ON),
                author.getValue(AUTHOR.UPDATED_ON),
                author.getValue(AUTHOR.IS_BANNED),
                author.getValue(AUTHOR.IS_ACTIVE)
        );
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
