package com.artorias.service;

import com.artorias.database.jooq.tables.pojos.Author;
import com.artorias.database.jooq.tables.pojos.Post;
import com.artorias.dto.PostDTO;
import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.artorias.database.jooq.tables.Author.AUTHOR;
import static com.artorias.database.jooq.tables.Post.POST;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by devin on 12/16/16.
 */
public class DefaultPostServiceTest extends BaseServiceTest<DefaultPostService, Post> {

    @Autowired
    ModelMapper modelMapper;

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
        Assert.assertEquals(service.table(), POST);
    }

    @Test
    public void list() {
        PostDTO dto = dto();
        Assert.assertEquals(service.list(1), Arrays.asList(dto));
    }

    //// utility methods /////
    private int getPostId(Post p) {
        return p.getPostId();
    }

    private Author expectedAuthor() {
        Timestamp t = ts();
        return new Author(1, "Test Author", "", "", t, t, null, null);
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
        expected.setAuthorId(1);
        expected.setAuthorName(expectedAuthor().getName());
        expected.setCreatedOn(ts());
        expected.setUpdatedOn(ts());
        expected.setPublishedOn(ts());
        return expected;
    }

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
    }

    private List<Map<String, Object>> buildPostMap() {
        Timestamp ts = new Timestamp(1481136454);
        Map<String, Object> m = ImmutableMap.<String, Object>builder()
                .put("post_id", "1")
                .put("title", "Test Post")
                .put("slug", "test-post")
                .put("body", "This is a test post")
                .put("author_id", "1")
                .put("created_on", ts.toString())
                .put("updated_on", ts.toString())
                .put("published_on", ts.toString())
                .put("AUTHOR_NAME", "Test Author")
                .build();
        return Arrays.asList(m);
    }


    //// end utility methods /////
}
