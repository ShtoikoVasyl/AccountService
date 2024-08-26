CREATE TABLE accounts (
                          id BIGSERIAL PRIMARY KEY,
                          owner_id BIGINT,
                          account_name VARCHAR(255),
                          account_number BIGINT,
                          pin_code SMALLINT,
                          currency_id BIGINT,
                          amount DECIMAL(19, 2),
                          status VARCHAR(50),
                          processing_status VARCHAR(50),

                          CONSTRAINT fk_currency
                              FOREIGN KEY (currency_id)
                                  REFERENCES currency(id)
);