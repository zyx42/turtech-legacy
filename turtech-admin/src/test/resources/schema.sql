CREATE SCHEMA IF NOT EXISTS turtech;

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

CREATE TABLE turtech.role (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) DEFAULT NULL
);

CREATE TABLE turtech.user_role (
  role_id BIGINT DEFAULT NULL REFERENCES role (id),
  user_id BIGINT DEFAULT NULL REFERENCES user (id),
  PRIMARY KEY (user_id, role_id)
);