--liquibase formatted sql

--changeset alex:1

CREATE TABLE cart_item
(
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY ,
    product_item_id int REFERENCES product(id) NOT NULL ,
    cart_id int REFERENCES cart(id) NOT NULL ,
    quantity int,
    created_at timestamp DEFAULT (now()),
    updated_at timestamp
)