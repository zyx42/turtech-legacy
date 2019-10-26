DROP SCHEMA IF EXISTS turtech;
CREATE SCHEMA turtech;

DROP TABLE IF EXISTS turtech.product CASCADE;

CREATE TABLE turtech.product (
  id SERIAL PRIMARY KEY,
  discontinued BOOLEAN NOT NULL,
  manufacturer VARCHAR(255) DEFAULT NULL,
  category VARCHAR(255) DEFAULT NULL,
  condition VARCHAR(255) default null,
  description TEXT,
  specifications TEXT,
  in_stock_number INT NOT NULL,
  list_price DOUBLE PRECISION NOT NULL,
  our_price DOUBLE PRECISION NOT NULL,
  manufacture_date DATE DEFAULT NULL,
  shipping_weight DOUBLE PRECISION NOT NULL,
  "name" VARCHAR(255) DEFAULT NULL,
  created_date TIMESTAMP NOT NULL,
  created_by VARCHAR(255) NOT NULL,
  last_modified_date TIMESTAMP NOT NULL,
  last_modified_by VARCHAR(255) NOT NULL
);

INSERT INTO turtech.product (discontinued, manufacturer, category, condition, 
description, specifications, in_stock_number, list_price, our_price, 
manufacture_date, shipping_weight, name, created_date, created_by, last_modified_date, last_modified_by)
VALUES
	(false, 'Apple', 'Laptops', 'New', 'Lorem ipsum dolor sit amet, 
consectetur adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque 
facere, soluta. Totam id dolores, sint aperiam sequi pariatur praesentium animi 
perspiciatis molestias iure, ducimus!', 'Lorem ipsum dolor sit amet, consectetur 
adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque facere, soluta. 
Totam id dolores, sint aperiam sequi pariatur praesentium animi perspiciatis 
molestias iure, ducimus!', 5, 1200.00, 999.00, '2018-10-24', 10.00, 'Apple 
McBook', '2018-10-24', 'admin', '2018-10-24', 'admin'),
	(false, 'Samsung', 'Cellphones', 'New', 'Lorem ipsum dolor sit amet, 
consectetur adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque 
facere, soluta. Totam id dolores, sint aperiam sequi pariatur praesentium animi 
perspiciatis molestias iure, ducimus!', 'Lorem ipsum dolor sit amet, consectetur 
adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque facere, soluta. 
Totam id dolores, sint aperiam sequi pariatur praesentium animi perspiciatis 
molestias iure, ducimus!', 5, 470.00, 390.00, '2018-10-24', 10.00, 'Samsung 
Galaxy S65', '2018-10-24', 'admin', '2018-10-24', 'admin'),
	(false, 'LG', 'Tablets', 'New', 'Lorem ipsum dolor sit amet, consectetur 
adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque facere, soluta. 
Totam id dolores, sint aperiam sequi pariatur praesentium animi perspiciatis 
molestias iure, ducimus!', 'Lorem ipsum dolor sit amet, consectetur adipisicing 
elit. Sapiente dicta fugit fugiat hic aliquam itaque facere, soluta. Totam id 
dolores, sint aperiam sequi pariatur praesentium animi perspiciatis molestias 
iure, ducimus!', 5, 300.00, 289.00, '2018-10-24', 10.00, 'LG Tablet', '2018-10-24', 'admin', '2018-10-24', 'admin'),
	(false, 'Dell', 'Laptops', 'New', 'Lorem ipsum dolor sit amet, 
consectetur adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque 
facere, soluta. Totam id dolores, sint aperiam sequi pariatur praesentium animi 
perspiciatis molestias iure, ducimus!', 'Lorem ipsum dolor sit amet, consectetur 
adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque facere, soluta. 
Totam id dolores, sint aperiam sequi pariatur praesentium animi perspiciatis 
molestias iure, ducimus!', 5, 450.00, 399.00, '2018-10-24', 10.00, 'Dell 
Latitude e6230', '2018-10-24', 'admin', '2018-10-24', 'admin'),
	(false, 'Nokia', 'Cellphones', 'New', 'Lorem ipsum dolor sit amet, 
consectetur adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque 
facere, soluta. Totam id dolores, sint aperiam sequi pariatur praesentium animi 
perspiciatis molestias iure, ducimus!', 'Lorem ipsum dolor sit amet, consectetur 
adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque facere, soluta. 
Totam id dolores, sint aperiam sequi pariatur praesentium animi perspiciatis 
molestias iure, ducimus!', 5, 12.00, 9.00, '2018-10-24', 10.00, 'Nokia 6230', '2018-10-24', 'admin', '2018-10-24', 'admin'),
	(false, 'Apple', 'Tablets', 'New', 'Lorem ipsum dolor sit amet, 
consectetur adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque 
facere, soluta. Totam id dolores, sint aperiam sequi pariatur praesentium animi 
perspiciatis molestias iure, ducimus!', 'Lorem ipsum dolor sit amet, consectetur 
adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque facere, soluta. 
Totam id dolores, sint aperiam sequi pariatur praesentium animi perspiciatis 
molestias iure, ducimus!', 5, 700.00, 699.00, '2018-10-24', 10.00, 'Apple 
Ipad', '2018-10-24', 'admin', '2018-10-24', 'admin'),
	(false, 'Asus', 'Laptops', 'New', 'Lorem ipsum dolor sit amet, 
consectetur adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque 
facere, soluta. Totam id dolores, sint aperiam sequi pariatur praesentium animi 
perspiciatis molestias iure, ducimus!', 'Lorem ipsum dolor sit amet, consectetur 
adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque facere, soluta. 
Totam id dolores, sint aperiam sequi pariatur praesentium animi perspiciatis 
molestias iure, ducimus!', 5, 1100.00, 899.00, '2018-10-24', 10.00, 'Asus 
N61Jv', '2018-10-24', 'admin', '2018-10-24', 'admin'),
	(false, 'Meizu', 'Cellphones', 'New', 'Lorem ipsum dolor sit amet, 
consectetur adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque 
facere, soluta. Totam id dolores, sint aperiam sequi pariatur praesentium animi 
perspiciatis molestias iure, ducimus!', 'Lorem ipsum dolor sit amet, consectetur 
adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque facere, soluta. 
Totam id dolores, sint aperiam sequi pariatur praesentium animi perspiciatis 
molestias iure, ducimus!', 5, 300.00, 259.00, '2018-10-24', 10.00, 'Meizy M2 
Note', '2018-10-24', 'admin', '2018-10-24', 'admin'),
	(false, 'Apple', 'Tablets', 'New', 'Lorem ipsum dolor sit amet, 
consectetur adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque 
facere, soluta. Totam id dolores, sint aperiam sequi pariatur praesentium animi 
perspiciatis molestias iure, ducimus!', 'Lorem ipsum dolor sit amet, consectetur 
adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque facere, soluta. 
Totam id dolores, sint aperiam sequi pariatur praesentium animi perspiciatis 
molestias iure, ducimus!', 5, 600.00, 449.00, '2018-10-24', 10.00, 'Apple Ipad 
Pro', '2018-10-24', 'admin', '2018-10-24', 'admin'),
	(false, 'Acer', 'Laptops', 'New', 'Lorem ipsum dolor sit amet, 
consectetur adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque 
facere, soluta. Totam id dolores, sint aperiam sequi pariatur praesentium animi 
perspiciatis molestias iure, ducimus!', 'Lorem ipsum dolor sit amet, consectetur 
adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque facere, soluta. 
Totam id dolores, sint aperiam sequi pariatur praesentium animi perspiciatis 
molestias iure, ducimus!', 5, 800.00, 569.00, '2018-10-24', 10.00, 'Acer 
Laptop', '2018-10-24', 'admin', '2018-10-24', 'admin'),
	(false, 'Xiaomi', 'Cellphones', 'New', 'Lorem ipsum dolor sit amet, 
consectetur adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque 
facere, soluta. Totam id dolores, sint aperiam sequi pariatur praesentium animi 
perspiciatis molestias iure, ducimus!', 'Lorem ipsum dolor sit amet, consectetur 
adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque facere, soluta. 
Totam id dolores, sint aperiam sequi pariatur praesentium animi perspiciatis 
molestias iure, ducimus!', 5, 400.00, 299.00, '2018-10-24', 10.00, 'Xiaomi 
Mi2', '2018-10-24', 'admin', '2018-10-24', 'admin');

DROP TABLE IF EXISTS turtech.user CASCADE;

CREATE TABLE turtech.user (
  id SERIAL PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  enabled BOOLEAN NOT NULL,
  first_name VARCHAR(255) DEFAULT NULL,
  last_name VARCHAR(255) DEFAULT NULL,
  password VARCHAR(255) DEFAULT NULL,
  phone VARCHAR(255) DEFAULT NULL,
  username VARCHAR(255) DEFAULT NULL,
  created_date TIMESTAMP DEFAULT NULL,
  created_by VARCHAR(255) DEFAULT NULL,
  last_modified_date TIMESTAMP DEFAULT NULL,
  last_modified_by VARCHAR(255) DEFAULT NULL
);

INSERT INTO turtech.user (email, enabled, first_name, last_name, password, phone, 
username, created_date, created_by, last_modified_date, last_modified_by)
VALUES
	('JAdams@gmail.com',true,'John','Adams','$2a$12$zM9ElzKshw.K4tnJYSzlDOKm5BtvhShEWC7ie3RLg3UBZYm1d133u', NULL, 'user',
  '2018-10-24', 'admin', '2018-10-24', 'admin'),
	('Admin@gmail.com',true,'Admin','Admin','$2a$12$zM9ElzKshw.K4tnJYSzlDOKm5BtvhShEWC7ie3RLg3UBZYm1d133u', NULL, 'admin',
  '2018-10-24', 'admin', '2018-10-24', 'admin');

DROP TABLE IF EXISTS turtech.role CASCADE;

CREATE TABLE turtech.role (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) DEFAULT NULL
);

INSERT INTO turtech.role (name)
VALUES
	('ROLE_ADMIN'),
	('ROLE_USER');

DROP TABLE IF EXISTS turtech.user_review cascade;

CREATE TABLE turtech.user_review (
	id SERIAL PRIMARY KEY,
	"text" TEXT,
	"timestamp" TIMESTAMP NOT NULL,
	user_id BIGINT NOT NULL REFERENCES "user" (id),
	product_id BIGINT NOT NULL REFERENCES product (id)
);

DROP TABLE IF EXISTS turtech.user_order CASCADE;

CREATE TABLE turtech.user_order (
  id SERIAL PRIMARY KEY,
  order_date TIMESTAMP DEFAULT NULL,
  order_status VARCHAR(255) DEFAULT NULL,
  order_total DECIMAL(19,2) DEFAULT NULL,
  shipping_date TIMESTAMP DEFAULT NULL,
  shipping_method VARCHAR(255) DEFAULT NULL,
  billing_address_id BIGINT DEFAULT NULL,
  payment_id BIGINT DEFAULT NULL,
  shipping_address_id BIGINT DEFAULT NULL,
  user_id BIGINT DEFAULT NULL REFERENCES "user" (id)
);

DROP TABLE IF EXISTS turtech.shopping_cart CASCADE;

CREATE TABLE turtech.shopping_cart (
  id SERIAL PRIMARY KEY,
  grand_total DECIMAL(19,2) DEFAULT NULL,
  user_id BIGINT DEFAULT NULL REFERENCES "user" (id)
);

INSERT INTO turtech.shopping_cart (grand_total, user_id)
VALUES
	(null, 1);

DROP TABLE IF EXISTS turtech.cart_item CASCADE;

CREATE TABLE turtech.cart_item (
  id SERIAL PRIMARY KEY,
  qty INT NOT NULL,
  subtotal DECIMAL(19,2) DEFAULT NULL,
  product_id BIGINT DEFAULT NULL REFERENCES product (id),
  order_id BIGINT DEFAULT NULL REFERENCES user_order (id),
  shopping_cart_id BIGINT DEFAULT NULL REFERENCES shopping_cart (id)
);

INSERT INTO turtech.shopping_cart (grand_total, user_id)
VALUES
	(NULL,2);

DROP TABLE IF EXISTS turtech.password_reset_token;

CREATE TABLE turtech.password_reset_token (
  id SERIAL PRIMARY KEY,
  expiry_date TIMESTAMP DEFAULT NULL,
  token VARCHAR(255) DEFAULT NULL,
  user_id BIGINT NOT NULL REFERENCES "user" (id)
);

DROP TABLE IF EXISTS turtech.payment;

CREATE TABLE turtech.payment (
  id SERIAL PRIMARY KEY,
  card_name VARCHAR(255) DEFAULT NULL,
  card_number VARCHAR(255) DEFAULT NULL,
  cvc INT NOT NULL,
  default_payment BOOLEAN DEFAULT false,
  expiry_month INT NOT NULL,
  expiry_year INT NOT NULL,
  holder_name VARCHAR(255) DEFAULT NULL,
  type VARCHAR(255) DEFAULT NULL,
  order_id BIGINT DEFAULT NULL REFERENCES user_order (id)
);

DROP TABLE IF EXISTS turtech.shipping_address;

CREATE TABLE turtech.shipping_address (
  id SERIAL PRIMARY KEY,
  shipping_address_city VARCHAR(255) DEFAULT NULL,
  shipping_address_country VARCHAR(255) DEFAULT NULL,
  shipping_address_name VARCHAR(255) DEFAULT NULL,
  shipping_address_street1 VARCHAR(255) DEFAULT NULL,
  shipping_address_street2 VARCHAR(255) DEFAULT NULL,
  shipping_address_zipcode VARCHAR(255) DEFAULT NULL,
  order_id BIGINT DEFAULT NULL REFERENCES user_order (id)
);

DROP TABLE IF EXISTS turtech.user_payment CASCADE;

CREATE TABLE turtech.user_payment (
  id SERIAL PRIMARY KEY,
  card_name VARCHAR(255) DEFAULT NULL,
  card_number VARCHAR(255) DEFAULT NULL,
  cvc INT NOT NULL,
  default_payment BOOLEAN DEFAULT FALSE,
  expiry_month INT NOT NULL,
  expiry_year INT NOT NULL,
  holder_name VARCHAR(255) DEFAULT NULL,
  type VARCHAR(255) DEFAULT NULL,
  user_id BIGINT DEFAULT NULL REFERENCES "user" (id)
);

DROP TABLE IF EXISTS turtech.user_billing;

CREATE TABLE turtech.user_billing (
  id SERIAL PRIMARY KEY,
  user_billing_city VARCHAR(255) DEFAULT NULL,
  user_billing_country VARCHAR(255) DEFAULT NULL,
  user_billing_name VARCHAR(255) DEFAULT NULL,
  user_billing_street1 VARCHAR(255) DEFAULT NULL,
  user_billing_street2 VARCHAR(255) DEFAULT NULL,
  user_billing_zipcode VARCHAR(255) DEFAULT NULL,
  user_payment_id BIGINT DEFAULT NULL REFERENCES user_payment (id)
);

DROP TABLE IF EXISTS turtech.billing_address;

CREATE TABLE turtech.billing_address (
  id SERIAL PRIMARY KEY,
  billing_address_city VARCHAR(255) DEFAULT NULL,
  billing_address_country VARCHAR(255) DEFAULT NULL,
  billing_address_name VARCHAR(255) DEFAULT NULL,
  billing_address_street1 VARCHAR(255) DEFAULT NULL,
  billing_address_street2 VARCHAR(255) DEFAULT NULL,
  billing_address_zipcode VARCHAR(255) DEFAULT NULL,
  order_id BIGINT DEFAULT NULL REFERENCES user_order (id)
);

DROP TABLE IF EXISTS turtech.user_role;

CREATE TABLE turtech.user_role (
  role_id BIGINT DEFAULT NULL REFERENCES role (id),
  user_id BIGINT DEFAULT NULL REFERENCES "user" (id),
  PRIMARY KEY (user_id, role_id)
);

INSERT INTO turtech.user_role (role_id, user_id)
VALUES
	(2,1),
	(1,2);


DROP TABLE IF EXISTS turtech.user_shipping;

CREATE TABLE turtech.user_shipping (
  id SERIAL PRIMARY KEY,
  user_shipping_city VARCHAR(255) DEFAULT NULL,
  user_shipping_country VARCHAR(255) DEFAULT NULL,
  user_shipping_default BOOLEAN NOT NULL,
  user_shipping_name VARCHAR(255) DEFAULT NULL,
  user_shipping_street1 VARCHAR(255) DEFAULT NULL,
  user_shipping_street2 VARCHAR(255) DEFAULT NULL,
  user_shipping_zipcode VARCHAR(255) DEFAULT NULL,
  user_id BIGINT DEFAULT NULL REFERENCES "user" (id)
);