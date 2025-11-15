CREATE TABLE users
(
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name     VARCHAR(255) NOT NULL,
    type     VARCHAR(32)  NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (email)
);