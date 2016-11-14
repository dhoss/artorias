package com.artorias.service;

import com.artorias.database.jooq.tables.pojos.Post;
import com.artorias.database.jooq.tables.records.PostRecord;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jvnet.hk2.annotations.Service;
import org.modelmapper.ModelMapper;

import static com.artorias.database.jooq.tables.Author.AUTHOR;
import static com.artorias.database.jooq.tables.Post.POST;

/**
 * Created by devin on 11/13/16.
 */
@Service
public class DefaultPostService extends BaseService<PostRecord, com.artorias.database.jooq.tables.Post, com.artorias.database.jooq.tables.pojos.Post> {
    @Override
    public Post find(String slug) {
        return this.dsl.select()
                .from(POST)
                .join(AUTHOR)
                .on(POST.AUTHOR_ID.equal(AUTHOR.AUTHOR_ID))
                .where(POST.SLUG.equal(slug))
                .fetchAny()
                .into(Post.class);
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
}
