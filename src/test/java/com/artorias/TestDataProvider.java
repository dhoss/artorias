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
        // You might need a DSLContext to create org.jooq.Result and org.jooq.Record objects
        DSLContext create = DSL.using(SQLDialect.POSTGRES_9_5);
        MockResult[] mock = new MockResult[1];
        String selectPostNoJoinRgx = "SELECT (\"\\w+\"\\.\"\\w+\"\\.\"\\w+\",?\\s)+FROM \"\\w+\"\\.\"\\w+\" WHERE \"\\w+\"\\.\"\\w+\"\\.\"\\w+\" =.+";
        String selectPostJoinAuthorRgx = "SELECT (\"\\w+\"\\.\"\\w+\"\\.\"\\w+\",?\\s)+FROM \"\\w+\"\\.\"\\w+\" JOIN \"\\w+\"\\.\"\\w+\" ON \"\\w+\"\\.\"\\w+\"\\.\"\\w+\" = \"\\w+\"\\.\"\\w+\"\\.\"\\w+\" WHERE \"\\w+\"\\.\"\\w+\"\\.\"\\w+\" =.+";

        // The execute context contains SQL string(s), bind values, and other meta-data
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

        else if (sql.toUpperCase().matches(selectPostJoinAuthorRgx)) {
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
        Field postId = DSL.field("POST_ID");
        Field postTitle = DSL.field("TITLE");
        Field postSlug = DSL.field("SLUG");
        Field postBody = DSL.field("BODY");
        Field postAuthor = DSL.field("AUTHOR_ID");
        //Field postCreatedOn = DSL.field("CREATED_ON");
        //Field postUpdatedOn = DSL.field("UPDATED_ON");
        Field postPublishedOn = DSL.field("PUBLISHED_ON");
        //Field authorId = DSL.field("AUTHOR_ID");
        Field authorName = DSL.field("NAME");


        Result<Record> result = create.newResult(postId, postTitle, postSlug, postBody, postAuthor, postPublishedOn, authorName);
        result.add(create.newRecord(POST));
        result.add(create.newRecord(AUTHOR));
        result.get(0).setValue(POST.POST_ID, 1);
        result.get(0).setValue(POST.TITLE, "Test Post");
        result.get(0).setValue(POST.SLUG, "test-post");
        result.get(0).setValue(POST.BODY, "This is a test post");
        result.get(0).setValue(POST.AUTHOR_ID, 1);
        //result.get(0).setValue(POST.CREATED_ON, ts);
        //result.get(0).setValue(POST.UPDATED_ON, ts);
        result.get(0).setValue(POST.PUBLISHED_ON, ts);
        result.get(1).setValue(AUTHOR.NAME, "Test Author");

        return result;
    }
}
