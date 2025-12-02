ALTER TABLE feedbacks
    DROP CONSTRAINT fk_feedbacks_on_teacher;

ALTER TABLE feedbacks
    DROP CONSTRAINT fk_feedbacks_on_pupil;

ALTER TABLE feedbacks
    DROP CONSTRAINT fk_feedbacks_on_subject;


ALTER TABLE feedbacks
    ADD CONSTRAINT fk_feedbacks_on_teacher FOREIGN KEY (teacher_id)
        REFERENCES teachers (id)
        ON DELETE CASCADE;

ALTER TABLE feedbacks
    ADD CONSTRAINT fk_feedbacks_on_pupil FOREIGN KEY (pupil_id)
        REFERENCES pupils (id)
        ON DELETE CASCADE;

ALTER TABLE feedbacks
    ADD CONSTRAINT fk_feedbacks_on_subject FOREIGN KEY (subject_id)
        REFERENCES subjects (id)
        ON DELETE CASCADE;