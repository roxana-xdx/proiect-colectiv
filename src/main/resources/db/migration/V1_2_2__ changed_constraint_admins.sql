ALTER TABLE admins DROP CONSTRAINT IF EXISTS FK_ADMINS_ON_EMAIL;

ALTER TABLE admins
    ADD CONSTRAINT fk_admins_on_email
        FOREIGN KEY (email)
            REFERENCES users(email)
            ON DELETE CASCADE;