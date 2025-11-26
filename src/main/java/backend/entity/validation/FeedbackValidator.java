package backend.entity.validation;

import backend.entity.Feedback;
import backend.entity.Pupil;
import backend.entity.Teacher;
import backend.repository.I_FeedbackRepository;
import backend.repository.I_PupilRepository;
import backend.repository.I_TeacherRepository;

import javax.security.auth.Subject;

public final class FeedbackValidator {

    private FeedbackValidator() {}

    public static void validateCreate(
            Long teacherId,
            Long pupilId,
            String message,
            int grade,
            I_TeacherRepository teacherRepo,
//            I_SubjectRepository subjectRepo,
            I_PupilRepository pupilRepo,
            I_FeedbackRepository feedbackRepo
    ) {

        if (teacherId == null) {
            throw new IllegalArgumentException("Teacher ID cannot be null");
        }
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new IllegalStateException("Teacher not found with id: " + teacherId));

        if (pupilId == null) {
            throw new IllegalArgumentException("Pupil ID cannot be null");
        }
        Pupil pupil = pupilRepo.findById(pupilId)
                .orElseThrow(() -> new IllegalStateException("Pupil not found with id: " + pupilId));

//        if (subjectId == null) {
//            throw new IllegalArgumentException("Subject ID cannot be null");
//        }
//        Subject subject = subjectRepo.findById(subjectId)
//                .orElseThrow(() -> new IllegalStateException("Subject not found with id: " + subjectId));


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
