-- Secuencias
CREATE SEQUENCE IF NOT EXISTS customer_order_seq INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE IF NOT EXISTS order_line_seq INCREMENT BY 1 START WITH 1;

-- Tabla customer_order
CREATE TABLE IF NOT EXISTS customer_order
(
    id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('customer_order_seq'),
    reference VARCHAR(255),
    total_amount NUMERIC(38, 2),
    payment_method VARCHAR(255),
    customer_id VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP NOT NULL
    );

-- Tabla order_line
CREATE TABLE IF NOT EXISTS order_line
(
    id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('order_line_seq'),
    order_id INTEGER NOT NULL,
    product_id INTEGER,
    quantity DOUBLE PRECISION,
    CONSTRAINT fk_orderline_order FOREIGN KEY (order_id) REFERENCES customer_order(id)
);