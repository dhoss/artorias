CREATE USER artorias_test PASSWORD 'artorias_test' LOGIN;

ALTER USER artorias_test CREATEDB;

GRANT CREATE ON DATABASE artorias_test TO artorias_test;

CREATE SCHEMA blog;

REVOKE CONNECT ON DATABASE artorias_test FROM PUBLIC;

ALTER SCHEMA blog OWNER TO artorias_test;

GRANT CONNECT
ON DATABASE artorias_test
TO artorias_test;

ALTER DEFAULT PRIVILEGES
    FOR ROLE artorias_test   -- Alternatively "FOR USER"
    IN SCHEMA blog
    GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO artorias_test;
