CREATE TABLE parents
(
    id    BIGINT       NOT NULL,
    email VARCHAR(255) NOT NULL,
    CONSTRAINT pk_parents PRIMARY KEY (id)
);

ALTER TABLE parents
    ADD CONSTRAINT uc_parents_email UNIQUE (email);

ALTER TABLE parents
    ADD CONSTRAINT FK_PARENTS_ON_EMAIL FOREIGN KEY (email) REFERENCES users (email);