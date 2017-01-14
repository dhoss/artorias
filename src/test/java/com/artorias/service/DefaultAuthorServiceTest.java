package com.artorias.service;

import com.artorias.database.jooq.tables.pojos.Author;
import com.artorias.database.jooq.tables.pojos.Post;
import com.artorias.dto.PostDTO;
import com.google.common.collect.ImmutableMap;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.artorias.database.jooq.tables.Author.AUTHOR;
import static com.artorias.database.jooq.tables.Post.POST;

/**
 * Created by devin on 12/16/16.
 */
public class DefaultAuthorServiceTest extends BaseServiceTest<DefaultAuthorService, Author> {

    @Autowired
    ModelMapper modelMapper;

    //// base methods /////
    @Override
    protected DefaultAuthorService setupService() {
        return new DefaultAuthorService(this.create, this.mockModelMapper);
    }

    @Override
    protected Author expectedRecord() {
        Timestamp t = ts();
        Author a = new Author(1, "Test Author", "test@test.com", "password123", t, t, true, false);
        return a;
    }

    @Override
    protected String searchTerm() {
        return "Test Author";
    }

    @Override
    protected void updateRecord(Author a, DefaultAuthorService service, String newData) {
        a.setName(newData);
        service.update(a);
    }

    @Override
    protected void deleteRecord(Author a, DefaultAuthorService service) {
        service.delete(a);
    }

    @Override
    protected void addRecord(Author a, DefaultAuthorService service) {
        service.add(a);
    }

    @Override
    protected Author findRecord(String searchTerm, DefaultAuthorService service) {
        return service.find(searchTerm);
    }

    @Override
    protected int getActualId(Author a) {
        return getAuthorId(a);
    }

    @Override
    protected int getExpectedId(Author a) {
        return getAuthorId(a);
    }

    //// end base methods /////

   /* @Test
    public void findWithRelated() {
        PostDTO expected = dto();
        // actual, expected
        Assert.assertEquals(
                service.findWithRelated(searchTerm()),
                expected
        );
    }*/

    @Test
    public void table() {
        Assert.assertEquals(service.table(), AUTHOR);
    }

    /*
    @Test
    public void list() {
        PostDTO dto = dto();
        Assert.assertEquals(service.list(1), Arrays.asList(dto));
    }*/

    //// utility methods /////
    private int getAuthorId(Author a) {
        return a.getAuthorId();
    }

    private Author expectedAuthor() {
        Timestamp t = ts();
        return new Author(1, "Test Author", "", "", t, t, null, null);
    }

    private Timestamp ts() {
        return new Timestamp(1481136454);
    }
/*
    private PostDTO dto() {
        PostDTO expected = new PostDTO();
        expected.setPostId(1);
        expected.setTitle("Test Post");
        expected.setSlug("test-post");
        expected.setBody("This is a test post");
        expected.setAuthorId(1);
        expected.setAuthorName(expectedAuthor().getName());
        expected.setCreatedOn(ts());
        expected.setUpdatedOn(ts());
        expected.setPublishedOn(ts());
        return expected;
    }*/

/*
    private Result<Record> buildSinglePostWithAuthorResult(DSLContext create) {
        Timestamp ts = new Timestamp(1481136454);
        Field postId = DSL.field(POST.POST_ID);
        Field postTitle = DSL.field(POST.TITLE);
        Field postSlug = DSL.field(POST.SLUG);
        Field postBody = DSL.field(POST.BODY);
        Field postAuthor = DSL.field(POST.AUTHOR_ID);
        Field postCreatedOn = DSL.field(POST.CREATED_ON);
        Field postUpdatedOn = DSL.field(POST.UPDATED_ON);
        Field postPublishedOn = DSL.field(POST.PUBLISHED_ON);
        Field authorName = DSL.field(AUTHOR.NAME);

        Result<Record> result = create.newResult(postId, postTitle, postSlug, postBody, postAuthor, postCreatedOn, postUpdatedOn, postPublishedOn, authorName);
        result.add(create.newRecord(postId, postTitle, postSlug, postBody, postAuthor, postCreatedOn, postUpdatedOn, postPublishedOn, authorName));
        result.get(0).setValue(POST.POST_ID, 1);
        result.get(0).setValue(POST.TITLE, "Test Post");
        result.get(0).setValue(POST.SLUG, "test-post");
        result.get(0).setValue(POST.BODY, "This is a test post");
        result.get(0).setValue(POST.AUTHOR_ID, 1);
        result.get(0).setValue(POST.CREATED_ON, ts);
        result.get(0).setValue(POST.UPDATED_ON, ts);
        result.get(0).setValue(POST.PUBLISHED_ON, ts);
        result.get(0).setValue(AUTHOR.NAME, "Test Author");

        return result;
    }*/


    //// end utility methods /////
}
