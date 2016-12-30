CREATE USER artorias_dev PASSWORD 'artorias_dev' LOGIN;

ALTER USER artorias_dev CREATEDB;

GRANT CREATE ON DATABASE artorias_dev TO artorias_dev;

CREATE SCHEMA blog;

REVOKE CONNECT ON DATABASE artorias_dev FROM PUBLIC;

ALTER SCHEMA blog OWNER TO artorias_dev;

GRANT CONNECT
ON DATABASE artorias_dev
TO artorias_dev;

ALTER DEFAULT PRIVILEGES
    FOR ROLE artorias_dev   -- Alternatively "FOR USER"
    IN SCHEMA blog
    GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO artorias_dev;
