--liquibase formatted sql

--changeset alex:1

ALTER TABLE cart
ADD COLUMN total_amount int DEFAULT 0