CREATE TABLE currency (
                          id BIGSERIAL PRIMARY KEY,
                          code VARCHAR(255) NOT NULL,
                          full_name VARCHAR(255) NOT NULL,
                          sign VARCHAR(10) UNIQUE
);