--liquibase formatted sql

--changeset alex:1

ALTER TABLE users
ADD COLUMN role varchar(16) DEFAULT 'USER'