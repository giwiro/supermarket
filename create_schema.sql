-- Create extensions schema
CREATE SCHEMA IF NOT EXISTS extensions;
GRANT USAGE ON SCHEMA extensions TO supermarket;

-- Create supermarket schema
CREATE SCHEMA supermarket AUTHORIZATION supermarket_admin;

GRANT USAGE ON SCHEMA supermarket TO supermarket;

GRANT SELECT ON ALL TABLES IN SCHEMA supermarket TO supermarket;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA supermarket TO supermarket_admin;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA supermarket TO supermarket;

ALTER DEFAULT PRIVILEGES IN SCHEMA supermarket GRANT SELECT ON TABLES TO supermarket;
ALTER DEFAULT PRIVILEGES IN SCHEMA supermarket GRANT USAGE, SELECT ON SEQUENCES TO supermarket;
ALTER DEFAULT PRIVILEGES IN SCHEMA supermarket GRANT INSERT, UPDATE, DELETE ON TABLES TO supermarket_admin;