package com.artorias;

import com.artorias.database.jooq.tables.records.PostRecord;
import lombok.extern.slf4j.Slf4j;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;

import java.sql.SQLException;
import java.sql.Timestamp;

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
        String selectPostNoJoinRgx = "SELECT (\"\\w+\"\\.\"\\w+\"\\.\"\\w+\",?\\s)+FROM \"\\w+\"\\.\"\\w+\" WHERE \"\\w+\"\\.\"\\w+\"\\.\"\\w+\" =.+";
        String selectPostJoinAuthorRgx = ".+ JOIN \"BLOG\".\"AUTHOR\" ON \"BLOG\".\"POST\".\"AUTHOR_ID\" .+";
        String listPostQueryRgx = "SELECT \"BLOG\".\"POST\".\"POST_ID\", \"BLOG\".\"POST\".\"TITLE\", \"BLOG\".\"POST\".\"SLUG\", \"BLOG\".\"POST\".\"BODY\", \"BLOG\".\"POST\".\"AUTHOR_ID\", \"BLOG\".\"POST\".\"CREATED_ON\", \"BLOG\".\"POST\".\"UPDATED_ON\", \"BLOG\".\"POST\".\"PUBLISHED_ON\", \"BLOG\".\"AUTHOR\".\"NAME\" AS \"AUTHOR_NAME\" FROM \"BLOG\".\"POST\", \"BLOG\".\"AUTHOR\" ORDER BY \"BLOG\".\"POST\".\"UPDATED_ON\" DESC, \"BLOG\".\"POST\".\"CREATED_ON\" DESC LIMIT";
        String selectCount = "SELECT COUNT(*) FROM \"BLOG\".\"POST\"";

        String sql = ctx.sql();


        // Exceptions are propagated through the JDBC and jOOQ APIs
        if (sql.toUpperCase().startsWith("DROP")) {
            throw new SQLException("Statement not supported: " + sql);
        }

        // find()
        else if (sql.toUpperCase().matches(selectPostNoJoinRgx)) {
            Result<PostRecord> result = buildSinglePostResult(create);
            mock[0] = new MockResult(1, result);
        }

        // findWithRelated()
        else if (sql.toUpperCase().matches(selectPostJoinAuthorRgx)){
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
        else if (sql.toUpperCase().contains(listPostQueryRgx)) {
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
