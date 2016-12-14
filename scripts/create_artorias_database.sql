CREATE USER artorias PASSWORD 'artorias' LOGIN;

ALTER USER artorias CREATEDB;

--CREATE DATABASE artorias;

CREATE SCHEMA blog;

REVOKE CONNECT ON DATABASE artorias FROM PUBLIC;

ALTER SCHEMA blog OWNER TO artorias;

GRANT CONNECT
ON DATABASE artorias
TO artorias;

ALTER DEFAULT PRIVILEGES
    FOR ROLE artorias   -- Alternatively "FOR USER"
    IN SCHEMA blog
    GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO artorias;
