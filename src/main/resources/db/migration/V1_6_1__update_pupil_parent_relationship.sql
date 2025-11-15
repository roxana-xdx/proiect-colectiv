ALTER TABLE pupil
    ADD CONSTRAINT FK_PUPIL_ON_PARENT FOREIGN KEY (parent_id) REFERENCES parents (id);

CREATE SEQUENCE IF NOT EXISTS parents_id_seq;
ALTER TABLE parents
    ALTER COLUMN id SET NOT NULL;
ALTER TABLE parents
    ALTER COLUMN id SET DEFAULT nextval('parents_id_seq');

ALTER SEQUENCE parents_id_seq OWNED BY parents.id;