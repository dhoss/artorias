/**
 * This class is generated by jOOQ
 */
package com.artorias.database.jooq;


import com.artorias.database.jooq.tables.Author;
import com.artorias.database.jooq.tables.Post;
import com.artorias.database.jooq.tables.SchemaVersion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Blog extends SchemaImpl {

    private static final long serialVersionUID = 757164781;

    /**
     * The reference instance of <code>blog</code>
     */
    public static final Blog BLOG = new Blog();

    /**
     * The table <code>blog.author</code>.
     */
    public final Author AUTHOR = com.artorias.database.jooq.tables.Author.AUTHOR;

    /**
     * The table <code>blog.post</code>.
     */
    public final Post POST = com.artorias.database.jooq.tables.Post.POST;

    /**
     * The table <code>blog.schema_version</code>.
     */
    public final SchemaVersion SCHEMA_VERSION = com.artorias.database.jooq.tables.SchemaVersion.SCHEMA_VERSION;

    /**
     * No further instances allowed
     */
    private Blog() {
        super("blog", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        List result = new ArrayList();
        result.addAll(getSequences0());
        return result;
    }

    private final List<Sequence<?>> getSequences0() {
        return Arrays.<Sequence<?>>asList(
            Sequences.AUTHOR_AUTHOR_ID_SEQ,
            Sequences.POST_POST_ID_SEQ);
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Author.AUTHOR,
            Post.POST,
            SchemaVersion.SCHEMA_VERSION);
    }
}
