package com.artorias.dto;

import com.artorias.database.jooq.tables.pojos.Author;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * Created by devin on 11/13/16.
 */

@Component
@Data
public class PostDTO {
    private int postId;
    private String title;
    private String slug;
    private String body;
    private String authorName;
    private int authorId;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Timestamp publishedOn;
}
