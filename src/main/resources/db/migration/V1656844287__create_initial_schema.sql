CREATE TABLE coin
(
    `id`   BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(255) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE coin_storage
(
    `id`      BIGINT(20)                  NOT NULL AUTO_INCREMENT,
    `name`    VARCHAR(255)                NOT NULL,
    `type`    ENUM ('WALLET', 'EXCHANGE') NOT NULL,
    `coin_id` BIGINT(20),
    PRIMARY KEY (id),
    CONSTRAINT FK_CoinStorage_Coin FOREIGN KEY (coin_id) REFERENCES coin (id)
);

CREATE TABLE transaction
(
    `id`                   BIGINT(20)                               NOT NULL AUTO_INCREMENT,
    `date`                 DATETIME                                 NOT NULL,
    `type`                 ENUM ('BUY', 'SELL', 'TRANSFER', 'SWAP') NOT NULL,
    `from_coin_storage_id` BIGINT(20),
    `to_coin_storage_id`   BIGINT(20),
    `coin_id`              BIGINT(20)                               NOT NULL,
    `fiat_amount`          DECIMAL(19, 4),
    `coin_amount`          DECIMAL(27, 18),
    `fee_fiat_amount`      DECIMAL(19, 4),
    `fee_coin_amount`      DECIMAL(27, 18),
    `comment`              TEXT,
    PRIMARY KEY (id),
    CONSTRAINT FK_Transaction_CoinStorage_from FOREIGN KEY (from_coin_storage_id) REFERENCES coin_storage (id),
    CONSTRAINT FK_Transaction_CoinStorage_to FOREIGN KEY (to_coin_storage_id) REFERENCES coin_storage (id),
    CONSTRAINT FK_Transaction_Coin FOREIGN KEY (coin_id) REFERENCES coin (id)
);
