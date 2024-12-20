--liquibase formatted sql

--changeset alex:1

CREATE TABLE users
(
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY ,
    full_name varchar(128) NOT NULL ,
    email varchar(320) unique NOT NULL ,
    password varchar(128) NOT NULL ,
    provider text,
    provider_id text,
    created_at timestamp DEFAULT (now()),
    updated_at timestamp
)