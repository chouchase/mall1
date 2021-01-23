CREATE TABLE mall_cart(
    id int PRIMARY KEY AUTO_INCREMENT,
    user_id int DEFAULT NULL,
    product_id int DEFAULT NULL,
    quantity int DEFAULT NULL,
    checked int DEFAULT NULL,
    create_time datetime DEFAULT NULL ,
    update_time datetime DEFAULT NULL,
    KEY user_id_index (user_id) USING BTREE
)AUTO_INCREMENT=121;