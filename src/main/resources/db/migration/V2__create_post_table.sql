CREATE TABLE post (
    post_id SERIAL PRIMARY KEY NOT NULL,
    title text NOT NULL,
    slug text NOT NULL,
    body text NOT NULL,
    author_id integer REFERENCES author(author_id),
    created_on timestamp with time zone DEFAULT now(),
    updated_on timestamp with time zone DEFAULT now(),
    published_on timestamp with time zone
);


CREATE INDEX post_title ON post(title);
CREATE INDEX post_slug ON post(slug);
CREATE INDEX post_author ON post(author_id);

CREATE INDEX post_created_on ON post(created_on DESC NULLS LAST);
CREATE INDEX post_updated_on ON post(updated_on DESC NULLS LAST);

CREATE INDEX post_published_on ON post(published_on DESC NULLS LAST);

CREATE INDEX post_published ON post(post_id) WHERE published_on IS NOT NULL;

