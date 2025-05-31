-- Crear secuencia para la tabla payment, incrementa de 1 en 1
CREATE SEQUENCE IF NOT EXISTS payment_seq
    INCREMENT BY 1
    START WITH 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS payment (
    id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('payment_seq'),
    amount NUMERIC(38, 2),
    payment_method VARCHAR(255),
    order_id INTEGER,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP
);