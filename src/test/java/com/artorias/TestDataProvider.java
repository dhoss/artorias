package com.artorias;

import com.artorias.database.jooq.tables.records.AuthorRecord;
import com.artorias.database.jooq.tables.records.PostRecord;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import static com.artorias.database.jooq.tables.Author.AUTHOR;
import static com.artorias.database.jooq.tables.Post.POST;

/**
 * Created by devin on 12/3/16.
 */

@Slf4j
public class TestDataProvider implements MockDataProvider {

    @Override
    public MockResult[] execute(MockExecuteContext ctx) throws SQLException {
        log.info("Using TestDataProvider");
        // You might need a DSLContext to create org.jooq.Result and org.jooq.Record objects
        DSLContext create = DSL.using(SQLDialect.POSTGRES_9_5);
        MockResult[] mock = new MockResult[1];

        // The execute context contains SQL string(s), bind values, and other meta-data
        String sql = ctx.sql();

        // Exceptions are propagated through the JDBC and jOOQ APIs
        if (sql.toUpperCase().startsWith("DROP")) {
            throw new SQLException("Statement not supported: " + sql);
        }

        // You decide, whether any given statement returns results, and how many
        else if (sql.toUpperCase().contains("SELECT")) {

            Result<PostRecord> result = create.newResult(POST);
            result.add(create.newRecord(POST));
            result.get(0).setValue(POST.POST_ID, 1);
            result.get(0).setValue(POST.TITLE, "Test Post");
            result.get(0).setValue(POST.SLUG, "test-post");
            result.get(0).setValue(POST.BODY, "This is a test post");
            result.get(0).setValue(POST.AUTHOR_ID, 1);
            result.get(0).setValue(POST.CREATED_ON, new Timestamp(1481136454));
            result.get(0).setValue(POST.UPDATED_ON, new Timestamp(1481136454));
            result.get(0).setValue(POST.PUBLISHED_ON, new Timestamp(1481136454));
            mock[0] = new MockResult(1, result);
        }

        return mock;
    }
}
