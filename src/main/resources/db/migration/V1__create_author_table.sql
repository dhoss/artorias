CREATE TABLE author(
  author_id SERIAL PRIMARY KEY NOT NULL,
  name TEXT NOT NULL,
  email TEXT NOT NULL,
  password TEXT NOT NULL,
  created_on timestamp with time zone DEFAULT now(),
  updated_on timestamp with time zone DEFAULT now(),
  is_active BOOLEAN DEFAULT FALSE,
  is_banned BOOLEAN DEFAULT FALSE
);

CREATE INDEX author_name ON author(name);

CREATE INDEX author_created_on ON author(created_on DESC NULLS LAST);
CREATE INDEX author_updated_on ON author(updated_on DESC NULLS LAST);

CREATE INDEX author_id_is_active  ON author(is_active) WHERE is_active IS TRUE;
CREATE INDEX author_id_is_banned  on author(is_banned) WHERE is_banned IS TRUE;

CREATE INDEX author_email_password ON author(email, password);

CREATE INDEX author_email_lower ON author(lower(email));