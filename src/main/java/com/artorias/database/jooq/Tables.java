/**
 * This class is generated by jOOQ
 */
package com.artorias.database.jooq;


import com.artorias.database.jooq.tables.Author;
import com.artorias.database.jooq.tables.SchemaVersion;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in blog
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>blog.author</code>.
     */
    public static final Author AUTHOR = com.artorias.database.jooq.tables.Author.AUTHOR;

    /**
     * The table <code>blog.schema_version</code>.
     */
    public static final SchemaVersion SCHEMA_VERSION = com.artorias.database.jooq.tables.SchemaVersion.SCHEMA_VERSION;
}
