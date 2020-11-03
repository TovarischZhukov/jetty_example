CREATE SCHEMA IF NOT EXISTS security;

CREATE TABLE IF NOT EXISTS security.users
(
    id SERIAL PRIMARY KEY,
    name varchar(100) NOT NULL UNIQUE,
    password varchar(50) NOT NULL
    );

CREATE TABLE IF NOT EXISTS security.roles
(
    id SERIAL PRIMARY KEY,
    role varchar(100) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS security.user_roles
(
    user_id integer NOT NULL,
    role_id integer NOT NULL,
    PRIMARY KEY (user_id, role_id)
    );