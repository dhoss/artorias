/**
 * This class is generated by jOOQ
 */
package com.artorias.database.jooq.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


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
public class Author implements Serializable {

    private static final long serialVersionUID = 554628430;

    private Integer   authorId;
    private String    name;
    private String    email;
    private String    password;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Boolean   isActive;
    private Boolean   isBanned;

    public Author() {}

    public Author(Author value) {
        this.authorId = value.authorId;
        this.name = value.name;
        this.email = value.email;
        this.password = value.password;
        this.createdOn = value.createdOn;
        this.updatedOn = value.updatedOn;
        this.isActive = value.isActive;
        this.isBanned = value.isBanned;
    }

    public Author(
        Integer   authorId,
        String    name,
        String    email,
        String    password,
        Timestamp createdOn,
        Timestamp updatedOn,
        Boolean   isActive,
        Boolean   isBanned
    ) {
        this.authorId = authorId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.isActive = isActive;
        this.isBanned = isBanned;
    }

    public Integer getAuthorId() {
        return this.authorId;
    }

    public Author setAuthorId(Integer authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Author setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return this.email;
    }

    public Author setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return this.password;
    }

    public Author setPassword(String password) {
        this.password = password;
        return this;
    }

    public Timestamp getCreatedOn() {
        return this.createdOn;
    }

    public Author setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Timestamp getUpdatedOn() {
        return this.updatedOn;
    }

    public Author setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Author setIsActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public Boolean getIsBanned() {
        return this.isBanned;
    }

    public Author setIsBanned(Boolean isBanned) {
        this.isBanned = isBanned;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Author (");

        sb.append(authorId);
        sb.append(", ").append(name);
        sb.append(", ").append(email);
        sb.append(", ").append(password);
        sb.append(", ").append(createdOn);
        sb.append(", ").append(updatedOn);
        sb.append(", ").append(isActive);
        sb.append(", ").append(isBanned);

        sb.append(")");
        return sb.toString();
    }
}