--liquibase formatted sql

--changeset alex:1
ALTER TABLE orders
ALTER COLUMN total_amount TYPE float
USING total_amount::float;

--changeset alex:2
ALTER TABLE orders
ADD COLUMN address varchar(256)