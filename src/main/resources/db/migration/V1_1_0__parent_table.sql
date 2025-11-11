DROP TABLE parent;

CREATE TABLE parent
(
    id BIGINT NOT NULL,
    email VARCHAR(255) NOT NULL,
    CONSTRAINT pk_parent PRIMARY KEY (id),
    CONSTRAINT uq_parent_email UNIQUE (email)
);