/* Shopping cart item status */
INSERT INTO supermarket.supermarket.shopping_cart_item_status (shopping_cart_item_status_id, shopping_cart_item_status_name)
VALUES (1, 'CREATED');

INSERT INTO supermarket.supermarket.shopping_cart_item_status (shopping_cart_item_status_id, shopping_cart_item_status_name)
VALUES (10, 'ARCHIVED');

/* Product categories */

INSERT INTO supermarket.supermarket.product_category (product_category_id, name)
VALUES
(1, 'Computers'),
(2, 'Cellphones'),
(3, 'Tablets'),
(4, 'Cameras'),
(5, 'Game Consoles'),
(6, 'Shoes'),
(7, 'Sports');

/* Sample products */
/* Laptops */
INSERT INTO supermarket.supermarket."product" (product_id, product_category_id, enabled, image_url, name, price)
VALUES (1, 1, true, 'https://images-na.ssl-images-amazon.com/images/I/81o8Tll-5-L._AC_SX679_.jpg', 'ASUS VivoBook L203MA Ultra-Thin Laptop, 11.6” HD, Intel Celeron N4000 Processor (up to 2.6 GHz), 4GB RAM, 64GB eMMC, USB-C, Windows 10 in S Mode, One Year of Microsoft Office 365, L203MA-DS04', 387.00);

INSERT INTO supermarket.supermarket."product" (product_id, product_category_id, enabled, image_url, name, price)
VALUES
(2, 1, true, 'https://images-na.ssl-images-amazon.com/images/I/41OfhZ7qhaL._AC_.jpg', 'Acer Aspire 1 A115-31 Slim Laptop Intel Processor N4000 4GB DDR4 64GB eMMC 15.6in HD LED Windows 10 in S Mode HDMI Webcam (Renewed)', 194.99),
(3, 1, true, 'https://images-na.ssl-images-amazon.com/images/I/51Q%2Bsj75EeL._AC_SX679_.jpg', '2020 HP 14“ Laptop (AMD A9-9425 up to 3.7 GHz, 4GB DDR4 RAM, 128GB SSD, AMD Radeon R5 Graphic, Wi-Fi, Bluetooth, HDMI, Windows 10 Home)', 413.99),
(4, 1, true, 'https://images-na.ssl-images-amazon.com/images/I/71thf1SYnGL._AC_SX679_.jpg', 'New Apple MacBook Air (13-inch, 8GB RAM, 256GB SSD Storage) - Gold', 949.99);

/* Cellphones */
INSERT INTO supermarket.supermarket."product" (product_id, product_category_id, enabled, image_url, name, price)
VALUES
(5, 2, true, 'https://images-na.ssl-images-amazon.com/images/I/71iO2R%2BCLjL._AC_SL1500_.jpg', 'Simple Mobile Prepaid - Apple Iphone 11 (64GB) - Black', 600.50),
(6, 2, true, 'https://images-na.ssl-images-amazon.com/images/I/81mxun%2B6pEL._AC_SX679_.jpg', 'Simple Mobile Prepaid - Apple iPhone 11 Pro Max (64GB) - Midnight Green [Locked to Carrier – Simple Mobile]', 1099.00),
(7, 2, true, 'https://images-na.ssl-images-amazon.com/images/I/616%2BkGLMqJL._AC_SX679_.jpg', 'Huawei P30 Lite (128GB, 4GB RAM) 6.15" Display, AI Triple Camera, 32MP Selfie, Dual SIM Global 4G LTE GSM Factory Unlocked MAR-LX3A - International Version (Midnight Black)', 219.00),
(8, 2, true, 'https://images-na.ssl-images-amazon.com/images/I/71WH8eMTCML._AC_SX679_.jpg', 'Oneplus 7 Pro GM1910 256GB, 8GB, Dual Sim, 6.67 inch, 48MP Main Lens Triple Camera, GSM Unlocked International Model, No Warranty (Nebula Blue)', 548.00);
