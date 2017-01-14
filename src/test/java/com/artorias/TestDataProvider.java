package com.artorias;

import com.artorias.database.jooq.tables.records.AuthorRecord;
import com.artorias.database.jooq.tables.records.PostRecord;
import lombok.extern.slf4j.Slf4j;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;

import javax.security.sasl.AuthorizeCallback;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.artorias.database.jooq.tables.Post.POST;
import static com.artorias.database.jooq.tables.Author.AUTHOR;

/**
 * Created by devin on 12/3/16.
 */

@Slf4j
public class TestDataProvider implements MockDataProvider {

    @Override
    public MockResult[] execute(MockExecuteContext ctx) throws SQLException {
        log.debug("Using TestDataProvider");
        DSLContext create = DSL.using(SQLDialect.POSTGRES_9_5);
        MockResult[] mock = new MockResult[1];
        String selectPostByIdRgx ="SELECT \"BLOG\".\"POST\".\"POST_ID\", \"BLOG\".\"POST\".\"TITLE\", \"BLOG\".\"POST\".\"SLUG\", \"BLOG\".\"POST\".\"BODY\", \"BLOG\".\"POST\".\"AUTHOR_ID\", \"BLOG\".\"POST\".\"CREATED_ON\", \"BLOG\".\"POST\".\"UPDATED_ON\", \"BLOG\".\"POST\".\"PUBLISHED_ON\" FROM \"BLOG\".\"POST\" WHERE \"BLOG\".\"POST\".\"POST_ID\" =";
        String selectPostBySlugRgx = "SELECT \"BLOG\".\"POST\".\"POST_ID\", \"BLOG\".\"POST\".\"TITLE\", \"BLOG\".\"POST\".\"SLUG\", \"BLOG\".\"POST\".\"BODY\", \"BLOG\".\"POST\".\"AUTHOR_ID\", \"BLOG\".\"POST\".\"CREATED_ON\", \"BLOG\".\"POST\".\"UPDATED_ON\", \"BLOG\".\"POST\".\"PUBLISHED_ON\" FROM \"BLOG\".\"POST\" WHERE \"BLOG\".\"POST\".\"SLUG\" =";
        String selectAuthorRgx = "SELECT \"BLOG\".\"AUTHOR\"";
        String selectPostJoinAuthorRgx = "SELECT \"BLOG\".\"POST\".\"POST_ID\", \"BLOG\".\"POST\".\"TITLE\", \"BLOG\".\"POST\".\"SLUG\", \"BLOG\".\"POST\".\"BODY\", \"BLOG\".\"POST\".\"AUTHOR_ID\", \"BLOG\".\"POST\".\"CREATED_ON\", \"BLOG\".\"POST\".\"UPDATED_ON\", \"BLOG\".\"POST\".\"PUBLISHED_ON\", \"BLOG\".\"AUTHOR\".\"NAME\" AS \"AUTHOR_NAME\" FROM \"BLOG\".\"POST\", \"BLOG\".\"AUTHOR\" JOIN \"BLOG\".\"AUTHOR\" ON \"BLOG\".\"POST\".\"AUTHOR_ID\" = \"BLOG\".\"AUTHOR\".\"AUTHOR_ID\" WHERE \"BLOG\".\"POST\".\"SLUG\"";
        String listPostQueryRgx = "SELECT \"BLOG\".\"POST\".\"POST_ID\", \"BLOG\".\"POST\".\"TITLE\", \"BLOG\".\"POST\".\"SLUG\", \"BLOG\".\"POST\".\"BODY\", \"BLOG\".\"POST\".\"AUTHOR_ID\", \"BLOG\".\"POST\".\"CREATED_ON\", \"BLOG\".\"POST\".\"UPDATED_ON\", \"BLOG\".\"POST\".\"PUBLISHED_ON\", \"BLOG\".\"AUTHOR\".\"NAME\" AS \"AUTHOR_NAME\" FROM \"BLOG\".\"POST\", \"BLOG\".\"AUTHOR\" ORDER BY \"BLOG\".\"POST\".\"UPDATED_ON\" DESC, \"BLOG\".\"POST\".\"CREATED_ON\" DESC LIMIT .+";
        String selectCount = "SELECT COUNT(*) FROM \"BLOG\".\"POST\"";

        String sql = ctx.sql();


        // Exceptions are propagated through the JDBC and jOOQ APIs
        if (sql.toUpperCase().startsWith("DROP")) {
            throw new SQLException("Statement not supported: " + sql);
        }

        // post find()
        else if (sql.toUpperCase().contains(selectPostByIdRgx)) {
            Result<PostRecord> result = buildSinglePostResult(create);
            mock[0] = new MockResult(1, result);
        }

        else if (sql.toUpperCase().contains(selectPostBySlugRgx)) {
            Result<PostRecord> result = buildSinglePostResult(create);
            mock[0] = new MockResult(1, result);
        }

        // author find()
        else if (sql.toUpperCase().contains(selectAuthorRgx)) {
            Result<AuthorRecord> result = buildSingleAuthorResult(create);
            mock[0] = new MockResult(1, result);
        }
        // findWithRelated()
        else if (sql.toUpperCase().contains(selectPostJoinAuthorRgx)){
            Result<Record> result = buildSinglePostWithAuthorResult(create);
            mock[0] = new MockResult(1, result);
        }

        // count
        else if (sql.toUpperCase().equals(selectCount)) {
            Field count = DSL.field("count(*)");
            Result<Record> result = create.newResult(count);
            result.add(create.newRecord(count)); // pass count to both newRecord and newResult or things get fucky
            result.get(0).setValue(DSL.field("count(*)", Integer.class),1);
            mock[0] = new MockResult(1, result);
        }

        // list(page)
        else if (sql.toUpperCase().matches(listPostQueryRgx)) {
            Result<Record> result = buildSinglePostWithAuthorResult(create);
            mock[0] = new MockResult(1, result);
        }

        // add()/delete()/update()
        else if (sql.toUpperCase().contains("INSERT") || sql.toUpperCase().contains("UPDATE") || sql.toUpperCase().contains("DELETE")) {
            mock[0] = new MockResult(0, null);
            return mock;
        }

        return mock;
    }

    private Result<PostRecord> buildSinglePostResult(DSLContext create) {
        Timestamp ts = new Timestamp(1481136454);
        Result<PostRecord> result = create.newResult(POST);
        result.add(create.newRecord(POST));
        result.get(0).setValue(POST.POST_ID, 1);
        result.get(0).setValue(POST.TITLE, "Test Post");
        result.get(0).setValue(POST.SLUG, "test-post");
        result.get(0).setValue(POST.BODY, "This is a test post");
        result.get(0).setValue(POST.AUTHOR_ID, 1);
        result.get(0).setValue(POST.CREATED_ON, ts);
        result.get(0).setValue(POST.UPDATED_ON, ts);
        result.get(0).setValue(POST.PUBLISHED_ON, ts);

        return result;
    }

    private Result<AuthorRecord> buildSingleAuthorResult(DSLContext create) {
        Timestamp ts = new Timestamp(1481136454);
        Result<AuthorRecord> result = create.newResult(AUTHOR);
        result.add(create.newRecord(AUTHOR));
        result.get(0).setValue(AUTHOR.AUTHOR_ID, 1);
        result.get(0).setValue(AUTHOR.NAME, "Test Author");
//        result.get(0).setValue(POST.SLUG, "test-post");
        result.get(0).setValue(AUTHOR.EMAIL, "test@test.com");
        result.get(0).setValue(AUTHOR.IS_ACTIVE, true);
        result.get(0).setValue(AUTHOR.CREATED_ON, ts);
        result.get(0).setValue(AUTHOR.UPDATED_ON, ts);
        result.get(0).setValue(AUTHOR.IS_BANNED, false);

        return result;
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
}
