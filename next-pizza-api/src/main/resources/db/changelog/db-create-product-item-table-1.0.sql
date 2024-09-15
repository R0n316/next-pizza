--liquibase formatted sql

--changeset alex:1

CREATE TABLE product_item
(
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY ,
    price int NOT NULL ,
    size int,
    pizza_type int,
    product_id int REFERENCES product(id) NOT NULL
)