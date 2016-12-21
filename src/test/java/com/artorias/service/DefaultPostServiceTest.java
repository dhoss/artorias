package com.artorias.service;

import com.artorias.database.jooq.tables.pojos.Author;
import com.artorias.database.jooq.tables.pojos.Post;
import com.artorias.dto.PostDTO;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.mockito.Mock;
import org.testng.Assert;

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

    @Test//(enabled=false)
    public void findWithRelated() {
        //PostDTO expected = dto();
        //Assert.assertEquals(
         //       expected,
                service.findWithRelated(searchTerm());
        //);
    }


    //// utility methods /////
    private int getPostId(Post p) {
        return p.getPostId();
    }

    private Author expectedAuthor() {
        Timestamp t = ts();
        return new Author(1, "test author", "test@test.com", "1234", t, t, true, false);
    }

    private Timestamp ts() {
        return new Timestamp(1481136454);
    }

    private PostDTO dto() {
        PostDTO expected = new PostDTO();
        expected.setPostId(1);
        expected.setTitle("test post");
        expected.setSlug("test-post");
        expected.setBody("test post body");
        expected.setAuthor(expectedAuthor());
        expected.setCreatedOn(ts());
        expected.setUpdatedOn(ts());
        expected.setPublishedOn(ts());
        return expected;
    }
    //// end utility methods /////

}
