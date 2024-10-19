--liquibase formatted sql

--changeset alex:1

ALTER TABLE refresh_token
DROP COLUMN expiration_date