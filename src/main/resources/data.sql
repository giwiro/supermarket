/* Shopping cart item status */
INSERT INTO supermarket.supermarket.shopping_cart_item_status (shopping_cart_item_status_id, shopping_cart_item_status_name)
VALUES (1, 'CREATED');

INSERT INTO supermarket.supermarket.shopping_cart_item_status (shopping_cart_item_status_id, shopping_cart_item_status_name)
VALUES (10, 'ARCHIVED');

/* Sample products */
INSERT INTO supermarket.supermarket."product" (product_id, enabled, image_url, name, price)
VALUES (1, true, 'https://images-na.ssl-images-amazon.com/images/I/71iO2R%2BCLjL._AC_SL1500_.jpg', 'Simple Mobile Prepaid - Apple Iphone 11 (64GB) - Black', 600.50);