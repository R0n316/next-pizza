--liquibase formatted sql

--changeset alex:1

CREATE TABLE cart_item_ingredient
(
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY ,
    cart_item_id int REFERENCES cart_item(id) NOT NULL ,
    ingredient_id int REFERENCES ingredient(id) NOT NULL ,
    UNIQUE (cart_item_id, ingredient_id)
)