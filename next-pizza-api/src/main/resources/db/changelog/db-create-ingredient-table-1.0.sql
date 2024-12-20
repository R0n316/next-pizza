--liquibase formatted sql

--changeset alex:1

CREATE TABLE ingredient
(
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY ,
    name varchar(64) UNIQUE NOT NULL ,
    price int NOT NULL ,
    image_url text NOT NULL
)