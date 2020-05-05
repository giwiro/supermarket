CREATE ROLE supermarket;
CREATE USER supermarket_admin WITH
  NOSUPERUSER
  NOCREATEDB
  PASSWORD 'changeme'
  IN ROLE supermarket;

CREATE DATABASE supermarket WITH OWNER supermarket_admin;
GRANT CONNECT ON DATABASE supermarket TO supermarket;
