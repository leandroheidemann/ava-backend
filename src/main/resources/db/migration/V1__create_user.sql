CREATE SEQUENCE seq_users;

CREATE TABLE "users"
(
    id       BIGINT       NOT NULL,
    name     VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
