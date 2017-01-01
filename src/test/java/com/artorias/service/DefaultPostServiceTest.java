package com.artorias.service;

import com.artorias.database.jooq.tables.pojos.Author;
import com.artorias.database.jooq.tables.pojos.Post;
import com.artorias.dto.PostDTO;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.artorias.database.jooq.tables.Post.POST;

import java.sql.Timestamp;

/**
 * Created by devin on 12/16/16.
 */
public class DefaultPostServiceTest extends BaseServiceTest<DefaultPostService, Post>{

    //// base methods /////
    @Override
    protected DefaultPostService setupService() {
        return new DefaultPostService(this.create, this.mockModelMapper);
    }

    @Override
    protected Post expectedRecord() {
        Timestamp t = ts();
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

    //// end base methods /////

    @Test
    public void findWithRelated() {
        PostDTO expected = dto();
        // actual, expected
        Assert.assertEquals(
                service.findWithRelated(searchTerm()),
                expected
        );
    }

    @Test
    public void table() {
        Assert.assertEquals(POST, service.table());
    }

    //// utility methods /////
    private int getPostId(Post p) {
        return p.getPostId();
    }

    private Author expectedAuthor() {
        Timestamp t = ts();
        return new Author(1, "Test Author", null, null, t, t, null, null);
    }

    private Timestamp ts() {
        return new Timestamp(1481136454);
    }

    private PostDTO dto() {
        PostDTO expected = new PostDTO();
        expected.setPostId(1);
        expected.setTitle("Test Post");
        expected.setSlug("test-post");
        expected.setBody("This is a test post");
        expected.setAuthor(expectedAuthor());
        expected.setCreatedOn(ts());
        expected.setUpdatedOn(ts());
        expected.setPublishedOn(ts());
        return expected;
    }
    //// end utility methods /////

}
