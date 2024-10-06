--liquibase formatted sql

--changeset alex:1

ALTER TABLE cart_item_ingredient
DROP CONSTRAINT cart_item_ingredient_cart_item_id_fkey,
ADD CONSTRAINT cart_item_ingredient_cart_item_id_fkey
FOREIGN KEY (cart_item_id)
REFERENCES cart_item (id)
ON DELETE CASCADE;
