CREATE TABLE product_status_history (
    id               VARCHAR(36)  NOT NULL,
    product_id       VARCHAR(36)  NOT NULL,
    previous_status  VARCHAR(50)  NOT NULL,
    new_status       VARCHAR(50)  NOT NULL,
    changed_by_type  ENUM('SYSTEM', 'REVIEWER', 'LABEL') NOT NULL,
    changed_by_id    VARCHAR(255) NULL,
    notes            TEXT         NULL,
    changed_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_history_product FOREIGN KEY (product_id) REFERENCES products (id)
);