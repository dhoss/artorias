package com.artorias.service;

import com.artorias.database.jooq.tables.pojos.Post;
import org.mockito.Mock;

import java.sql.Timestamp;

/**
 * Created by devin on 12/16/16.
 */
public class DefaultPostServiceTest extends BaseServiceTest<DefaultPostService, Post>{

    @Mock
    Post expectedPost;

    @Override
    protected DefaultPostService setupService() {
        return new DefaultPostService(this.create, this.mockModelMapper);
    }

    @Override
    protected Post expectedRecord() {
        Timestamp t = new Timestamp(1481136454);
        Post p = new Post(1, "Test Post", "test-post", "This is a test post", 1, t, t, t);
        return p;
    }

    @Override
    protected String searchTerm() {
        return "test-post";
    }

    @Override
    protected void updateRecord(Post p, DefaultPostService service, String newData) {
        p.setTitle(newData);
        service.update(p);
    }

    @Override
    protected void deleteRecord(Post p, DefaultPostService service) {
        service.delete(p);
    }

    @Override
    protected void addRecord(Post p, DefaultPostService service) {
        service.add(p);
    }

    @Override
    protected Post findRecord(String searchTerm, DefaultPostService service) {
        return service.find(searchTerm);
    }

    @Override
    protected int getActualId(Post p) {
        return getPostId(p);
    }

    @Override
    protected int getExpectedId(Post p) {
        return getPostId(p);
    }

    private int getPostId(Post p) {
        return p.getPostId();
    }

}
