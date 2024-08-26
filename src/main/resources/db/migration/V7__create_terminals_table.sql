CREATE TABLE terminals (
                           id BIGINT PRIMARY KEY,         -- Використання BIGINT для зберігання шестизначного ідентифікатора
                           name VARCHAR(255) NOT NULL,    -- Поле для імені терміналу
                           address VARCHAR(255),          -- Поле для адреси терміналу
                           password VARCHAR(255),         -- Поле для зберігання пароля
                           signature VARCHAR(255),        -- Поле для підпису терміналу
                           last_activity TIMESTAMP WITH TIME ZONE  -- Поле для зберігання часу останньої активності
);