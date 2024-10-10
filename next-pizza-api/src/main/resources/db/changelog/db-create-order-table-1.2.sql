--liquibase formatted sql

--changeset alex:1
ALTER TABLE orders
ADD COLUMN secret text