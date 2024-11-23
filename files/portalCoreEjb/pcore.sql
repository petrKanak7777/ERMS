/* Create role portal_core (simple way, how to create something like user with all privileges) */
/* todo:
  - specific privileges (INSERT, UPDATE, DELETE records only on some tables)
  - encrypted password
 */
CREATE ROLE "portal_core" WITH
    LOGIN
    SUPERUSER
    INHERIT
    CREATEDB
    CREATEROLE
    REPLICATION
    BYPASSRLS
    PASSWORD 'heslo1234';

/* Create tablespace - physical organization tool */
 -- mkdir /var/lib/postgresql/data/pcore
 -- chown postgres:postgres /var/lib/postgresql/data/pcore
-- DROP TABLESPACE IF EXISTS portal_core_ts;
CREATE TABLESPACE portal_core_ts LOCATION '/var/lib/postgresql/data/pcore';
ALTER TABLESPACE portal_core_ts
  OWNER TO "portal_core";

/* Create database portal_core */
CREATE DATABASE "portal_core"
    WITH
    OWNER = "portal_core"
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = portal_core_ts
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

/* Create schema - logical organization tool */
CREATE SCHEMA IF NOT EXISTS portal_core
    AUTHORIZATION portal_core;

COMMENT ON SCHEMA portal_core
    IS 'portal_core schema';

GRANT ALL ON SCHEMA portal_core TO portal_core;

/* Create table employee and add records into */
-- DROP TABLE IF EXISTS public.employee;

CREATE TABLE IF NOT EXISTS portal_core.employee
(
    employee_id uuid NOT NULL DEFAULT gen_random_uuid(),
    first_name character varying COLLATE pg_catalog."default" NOT NULL,
    last_name character varying COLLATE pg_catalog."default" NOT NULL,
    email character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT employee_id_pkey PRIMARY KEY (employee_id)
)

ALTER TABLE IF EXISTS portal_core.employee
    OWNER to "portal_core";

-- insert 3 records
INSERT INTO portal_core.employee (first_name, last_name, email)
VALUES ('Pavel', 'Back', 'pavel.back123@gmail.com');

INSERT INTO portal_core.employee (first_name, last_name, email)
VALUES ('Jirka', 'Novotný', 'jirka.novotny456@gmail.com');

INSERT INTO portal_core.employee (first_name, last_name, email)
VALUES ('Libor', 'Souček', 'libor.soucek789@gmail.com');