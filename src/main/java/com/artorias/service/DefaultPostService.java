package com.artorias.service;

import com.artorias.database.jooq.tables.pojos.Post;
import com.artorias.database.jooq.tables.records.PostRecord;
import com.artorias.dto.PostDTO;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
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
                //.join(AUTHOR)
                //.on(POST.AUTHOR_ID.equal(AUTHOR.AUTHOR_ID))
                .where(POST.SLUG.equal(slug))
                .fetchAny()
                .into(Post.class);
    }

    @Override
    public PostRecord buildRecord(Post post) {
        ModelMapper mapper = new ModelMapper();
        System.out.println("**********POST OBJECT IN BUILDRECORD " + post);
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

    @Override
    public List<PostDTO> asDto(List<Post> results) {
        java.lang.reflect.Type targetListType = new TypeToken<List<PostDTO>>() {
        }.getType();
        return mapper.map(results, targetListType);
    }

    public List<PostDTO> listAsDto(int pageNumber) {
        return asDto(list(pageNumber));
    }
}
