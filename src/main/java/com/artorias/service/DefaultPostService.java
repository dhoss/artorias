package com.artorias.service;

import com.artorias.database.jooq.tables.pojos.Post;
import com.artorias.database.jooq.tables.records.PostRecord;
import com.artorias.dto.PostDTO;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jvnet.hk2.annotations.Service;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.artorias.database.jooq.tables.Author.AUTHOR;
import static com.artorias.database.jooq.tables.Post.POST;

/**
 * Created by devin on 11/13/16.
 */

@Slf4j
@Service
public class DefaultPostService extends BaseJooqService<PostRecord, com.artorias.database.jooq.tables.Post, Post, PostDTO> {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private DSLContext dsl;

    public DefaultPostService(DSLContext d, ModelMapper map) {
        this.mapper = map;
        this.dsl = d;
    }

    @Override
    public Post find(String slug) {
        return this.dsl.select()
                .from(POST)
                .where(POST.SLUG.equal(slug))
                .fetchAny()
                .into(Post.class);
    }

    public Record findWithRelated(String slug) {
        return this.dsl.select(POST.POST_ID, POST.TITLE, POST.SLUG, POST.BODY, POST.AUTHOR_ID, POST.CREATED_ON, POST.UPDATED_ON, POST.PUBLISHED_ON, AUTHOR.NAME)
                .from(POST, AUTHOR)
                .join(AUTHOR)
                .on(POST.AUTHOR_ID.equal(AUTHOR.AUTHOR_ID))
                .where(POST.SLUG.equal(slug))
                .fetchAny();
    }

    @Override
    public PostRecord buildRecord(Post post) {
        ModelMapper mapper = new ModelMapper();
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

    // could probably generalize these in the base class
    @Override
    public List<PostDTO> listAsDto(List<Post> results) {
        java.lang.reflect.Type targetListType = new TypeToken<List<PostDTO>>() {
        }.getType();
        return mapper.map(results, targetListType);
    }

    @Override
    public PostDTO singleAsDto(Post result) {
        java.lang.reflect.Type targetListType = new TypeToken<PostDTO>() {
        }.getType();
        return mapper.map(result, targetListType);
    }

    public List<PostDTO> pagedListAsDto(int pageNumber) {
        log.debug("******* HIT PAGEDLISTASDTO");
        return listAsDto(list(pageNumber));
    }
}
