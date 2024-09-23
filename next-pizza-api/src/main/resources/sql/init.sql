INSERT INTO users(full_name, email, password, verified)
VALUES
('user', 'user@gmail.com', '123', now()),
('admin', 'admin@gmail.com', '123', now());

INSERT INTO category(name)
VALUES
('Пиццы'),
('Завтрак'),
('Закуски'),
('Коктейли'),
('Напитки');

INSERT INTO ingredient (name, price, image_url)
VALUES
    ('Сырный бортик', 179, 'https://cdn.dodostatic.net/static/Img/Ingredients/99f5cb91225b4875bd06a26d2e842106.png'),
    ('Сливочная моцарелла', 79, 'https://cdn.dodostatic.net/static/Img/Ingredients/cdea869ef287426386ed634e6099a5ba.png'),
    ('Сыры чеддер и пармезан', 79, 'https://cdn.dodostatic.net/static/Img/Ingredients/000D3A22FA54A81411E9AFA69C1FE796'),
    ('Острый перец халапеньо', 59, 'https://cdn.dodostatic.net/static/Img/Ingredients/11ee95b6bfdf98fb88a113db92d7b3df.png'),
    ('Нежный цыпленок', 79, 'https://cdn.dodostatic.net/static/Img/Ingredients/000D3A39D824A82E11E9AFA5B328D35A'),
    ('Шампиньоны', 59, 'https://cdn.dodostatic.net/static/Img/Ingredients/000D3A22FA54A81411E9AFA67259A324'),
    ('Ветчина', 79, 'https://cdn.dodostatic.net/static/Img/Ingredients/000D3A39D824A82E11E9AFA61B9A8D61'),
    ('Пикантная пепперони', 79, 'https://cdn.dodostatic.net/static/Img/Ingredients/000D3A22FA54A81411E9AFA6258199C3'),
    ('Острая чоризо', 79, 'https://cdn.dodostatic.net/static/Img/Ingredients/000D3A22FA54A81411E9AFA62D5D6027'),
    ('Маринованные огурчики', 59, 'https://cdn.dodostatic.net/static/Img/Ingredients/000D3A21DA51A81211E9EA89958D782B'),
    ('Свежие томаты', 59, 'https://cdn.dodostatic.net/static/Img/Ingredients/000D3A39D824A82E11E9AFA7AC1A1D67'),
    ('Красный лук', 59, 'https://cdn.dodostatic.net/static/Img/Ingredients/000D3A22FA54A81411E9AFA60AE6464C'),
    ('Сочные ананасы', 59, 'https://cdn.dodostatic.net/static/Img/Ingredients/000D3A21DA51A81211E9AFA6795BA2A0'),
    ('Итальянские травы', 39, 'https://cdn.dodostatic.net/static/Img/Ingredients/370dac9ed21e4bffaf9bc2618d258734.png'),
    ('Сладкий перец', 59, 'https://cdn.dodostatic.net/static/Img/Ingredients/000D3A22FA54A81411E9AFA63F774C1B'),
    ('Кубики брынзы', 79, 'https://cdn.dodostatic.net/static/Img/Ingredients/000D3A39D824A82E11E9AFA6B0FFC349'),
    ('Митболы', 79, 'https://cdn.dodostatic.net/static/Img/Ingredients/b2f3a5d5afe44516a93cfc0d2ee60088.png');

INSERT INTO product (name, image_url, category_id)
VALUES
    ('Омлет с ветчиной и грибами', 'https://media.dodostatic.net/image/r:292x292/11EE7970321044479C1D1085457A36EB.webp', 2),
    ('Омлет с пепперони', 'https://media.dodostatic.net/image/r:292x292/11EE94ECF33B0C46BA410DEC1B1DD6F8.webp', 2),
    ('Кофе Латте', 'https://media.dodostatic.net/image/r:292x292/11EE7D61B0C26A3F85D97A78FEEE00AD.webp', 2),
    ('Дэнвич ветчина и сыр', 'https://media.dodostatic.net/image/r:292x292/11EE796FF0059B799A17F57A9E64C725.webp', 3),
    ('Куриные наггетсы', 'https://media.dodostatic.net/image/r:292x292/11EE7D618B5C7EC29350069AE9532C6E.webp', 3),
    ('Картофель из печи с соусом 🌱', 'https://media.dodostatic.net/image/r:292x292/11EED646A9CD324C962C6BEA78124F19.webp', 3),
    ('Додстер', 'https://media.dodostatic.net/image/r:292x292/11EE796F96D11392A2F6DD73599921B9.webp', 3),
    ('Острый Додстер 🌶️🌶️', 'https://media.dodostatic.net/image/r:292x292/11EE796FD3B594068F7A752DF8161D04.webp', 3),
    ('Банановый молочный коктейль', 'https://media.dodostatic.net/image/r:292x292/11EEE20B8772A72A9B60CFB20012C185.webp', 4),
    ('Карамельное яблоко молочный коктейль', 'https://media.dodostatic.net/image/r:292x292/11EE79702E2A22E693D96133906FB1B8.webp', 4),
    ('Молочный коктейль с печеньем Орео', 'https://media.dodostatic.net/image/r:292x292/11EE796FA1F50F8F8111A399E4C1A1E3.webp', 4),
    ('Классический молочный коктейль 👶', 'https://media.dodostatic.net/image/r:292x292/11EE796F93FB126693F96CB1D3E403FB.webp', 4),
    ('Ирландский Капучино', 'https://media.dodostatic.net/image/r:292x292/11EE7D61999EBDA59C10E216430A6093.webp', 5),
    ('Кофе Карамельный капучино', 'https://media.dodostatic.net/image/r:292x292/11EE7D61AED6B6D4BFDAD4E58D76CF56.webp', 5),
    ('Кофе Кокосовый латте', 'https://media.dodostatic.net/image/r:292x292/11EE7D61B19FA07090EE88B0ED347F42.webp', 5),
    ('Кофе Американо', 'https://media.dodostatic.net/image/r:292x292/11EE7D61B044583596548A59078BBD33.webp', 5),
    ('Кофе Латте', 'https://media.dodostatic.net/image/r:292x292/11EE7D61B0C26A3F85D97A78FEEE00AD.webp', 5);

INSERT INTO product(name, image_url, category_id)
VALUES
('Пепперони фреш', 'https://media.dodostatic.net/image/r:584x584/11EE7D6130241E75B0AB33725248C0D0.avif', 1),
('Сырная', 'https://media.dodostatic.net/image/r:584x584/11EE7D610D91679BB519F38C3F45880F.avif', 1),
('Чоризо фреш', 'https://media.dodostatic.net/image/r:584x584/11EE7D61706D472F9A5D71EB94149304.webp', 1);


INSERT INTO ingredient_product(product_id, ingredient_id)
VALUES
(18,1),
(18,2),
(18,3),
(18,4),
(18,5),
(19,6),
(19,7),
(19,8),
(19,9),
(19,10),
(20,11),
(20,12),
(20,13),
(20,14),
(20,15),
(20,16),
(20,17);

INSERT INTO product_item(product_id, pizza_type, price, size)
VALUES
-- Пицца "Пепперони фреш"
(18, 0, 320, 20),
(18, 1, 440, 30),
(18, 1, 570, 40),
-- Пицца "Сырная"
(19, 0, 240, 20),
(19, 0, 320, 30),
(19, 0, 370, 40),
(19, 1, 250, 20),
(19, 1, 340, 30),
(19, 1, 390, 40),
-- Пицца "Чоризо фреш"
(20, 0, 299, 20),
(20, 1, 529, 30),
(20, 1, 659, 40);

-- Остальные продукты
INSERT INTO product_item(product_id, price)
VALUES
(1, 429),
(2, 519),
(3, 359),
(4, 569),
(5, 209),
(6, 449),
(7, 309),
(8, 539),
(9, 289),
(10, 489),
(11, 379),
(12, 549),
(13, 239),
(14, 409),
(15, 399),
(16, 579),
(17, 249);


INSERT INTO cart(user_id, token, total_amount)
VALUES
(1, '11111', 650),
(2, '22222', 1000);

INSERT INTO cart_item(product_item_id, cart_id, quantity)
VALUES
(1, 1, 2);


INSERT INTO cart_item_ingredient(cart_item_id, ingredient_id)
VALUES
(1, 1),
(1, 2),
(1, 3);