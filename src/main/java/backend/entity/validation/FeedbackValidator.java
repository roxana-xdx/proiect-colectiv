package backend.entity.validation;

import backend.repository.I_PupilRepository;
import backend.repository.I_SubjectRepository;
import backend.repository.I_TeacherRepository;

public final class FeedbackValidator {

    private FeedbackValidator() {}

    public static void validateCreate(
            Long teacherId,
            Long pupilId,
            Long subjectId,
            String message,
            int grade,
            I_TeacherRepository teacherRepo,
            I_PupilRepository pupilRepo,
            I_SubjectRepository subjectRepo
    ) {

        if (teacherId == null) throw new IllegalArgumentException("Teacher ID cannot be null");
        if (!teacherRepo.existsById(teacherId)) {
            throw new IllegalStateException("Teacher not found with id: " + teacherId);
        }

        if (pupilId == null) throw new IllegalArgumentException("Pupil ID cannot be null");
        if (!pupilRepo.existsById(pupilId)) {
            throw new IllegalStateException("Pupil not found with id: " + pupilId);
        }

        if (subjectId == null) throw new IllegalArgumentException("Subject ID cannot be null");
        if (!subjectRepo.existsById(subjectId)) {
            throw new IllegalStateException("Subject not found with id: " + subjectId);
        }

        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        if (message.length() > 255) {
            throw new IllegalArgumentException("Message cannot exceed 255 characters");
        }

        if (grade < 1 || grade > 10) {
            throw new IllegalArgumentException("Grade must be between 1 and 10");
        }
    }
}