CREATE TABLE item
(
    item_id                  SERIAL PRIMARY KEY,
    item_name                TEXT             NOT NULL,
    item_category            TEXT             NOT NULL,
    item_price               DOUBLE PRECISION NOT NULL,
    item_quantity_remain     INT              NOT NULL,
    Item_quantity_total_sold INT              NOT NULL,
    CHECK ( item_category IN ('Drink', 'Chocolate', 'Chip', 'Candy'))
);


CREATE TABLE "user"
(
    user_id           SERIAL PRIMARY KEY,
    user_name         TEXT NOT NULL,
    user_password     TEXT NOT NULL,
    card_holder_name  TEXT NOT NULL,
    card_number       INT  NOT NULL,
    user_type         TEXT NOT NULL,
    user_last_5_items TEXT NOT NULL,
    CHECK ( user_type IN ('Customer', 'Seller', 'Cashier', 'Owner', 'Anonymous'))
);


CREATE TABLE change
(
    change_id       SERIAL PRIMARY KEY,
    change_type     DOUBLE PRECISION NOT NULL,
    change_quantity INT              NOT NULL
);
CREATE TABLE transaction
(
    transaction_id     SERIAL PRIMARY KEY,
    transaction_date   DATE                            NOT NULL,
    transaction_time   TIME                            NOT NULL,
    transaction_amount DOUBLE PRECISION                NOT NULL,
    transaction_items  TEXT                            NOT NULL,
    transaction_change DOUBLE PRECISION                NOT NULL,
    transaction_method TEXT                            NOT NULL,
    user_id            INT REFERENCES "user" (user_id) NULL,
    CHECK ( transaction_method IN ('Cash', 'Card'))
);


CREATE TABLE cancelledTransaction
(
    cancelled_transaction_id     SERIAL PRIMARY KEY,
    cancelled_transaction_date   DATE                            NOT NULL,
    cancelled_transaction_time   TIME                            NOT NULL,
    user_id                      INT REFERENCES "user" (user_id) NULL,
    cancelled_transaction_reason TEXT                            NOT NULL,
    CHECK (cancelled_transaction_reason IN ('timeout', 'user cancelled', 'change not available') )
)