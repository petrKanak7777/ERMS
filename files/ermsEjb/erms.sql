/*CREATE ROLE "erms" WITH
    LOGIN
    SUPERUSER
    INHERIT
    CREATEDB
    CREATEROLE
    REPLICATION
    BYPASSRLS
    PASSWORD 'heslo1234';*/

/* Create tablespace - physical organization tool */
-- mkdir /var/lib/postgresql/data/erms
-- chown postgres:postgres /var/lib/postgresql/data/erms
-- DROP TABLESPACE IF EXISTS erms_ts;
CREATE TABLESPACE erms_ts LOCATION '/var/lib/postgresql/data/erms';
ALTER TABLESPACE erms_ts
  OWNER TO "erms";

/* Create database erms */
/*CREATE DATABASE "erms"
    WITH
    OWNER = "erms"
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = erms_ts
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;*/

/* Create schema - logical organization tool */
CREATE SCHEMA IF NOT EXISTS erms
    AUTHORIZATION erms;

COMMENT ON SCHEMA erms
    IS 'erms schema';

GRANT ALL ON SCHEMA erms TO erms;

CREATE TABLE IF NOT EXISTS erms.file
(
    file_id uuid default gen_random_uuid(),
    user_id uuid null,
    name    varchar(256) not null,
    primary key (file_id)
);

ALTER TABLE IF EXISTS erms.file
    OWNER to "erms";

INSERT INTO erms.file (user_id, name)
values ('940be04d-f0a4-4408-b120-93e2e400f72c', 'File0'),
       ('00b48cba-ce22-4975-b961-d7dade3bcd90', 'File1')