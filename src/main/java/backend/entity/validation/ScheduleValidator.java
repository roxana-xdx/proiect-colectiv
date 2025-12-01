package backend.entity.validation;

import backend.repository.I_TeacherRepository;
import backend.repository.I_SubjectRepository;
import backend.repository.I_SchoolClassRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public final class ScheduleValidator {

    private ScheduleValidator() { /* utility class */ }

    public static void validateDateAndTime(LocalDate date, LocalTime startHour, LocalTime endHour) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (startHour == null) {
            throw new IllegalArgumentException("Start hour cannot be null");
        }
        if (endHour == null) {
            throw new IllegalArgumentException("End hour cannot be null");
        }

        LocalDate today = LocalDate.now();
        if (date.isBefore(today)) {
            throw new IllegalArgumentException("Date cannot be in the past");
        }

        if (!endHour.isAfter(startHour)) {
            throw new IllegalArgumentException("End hour must be strictly after start hour");
        }
    }

    public static void validateExistence(
            Long teacherId,
            Long subjectId,
            Long classId,
            I_TeacherRepository teacherRepo,
            I_SubjectRepository subjectRepo,
            I_SchoolClassRepository schoolClassRepo) {

        if (teacherId != null && !teacherRepo.existsById(teacherId)) {
            throw new IllegalStateException("Teacher not found with ID: " + teacherId);
        }
        if (subjectId != null && !subjectRepo.existsById(subjectId)) {
            throw new IllegalStateException("Subject not found with ID: " + subjectId);
        }
        if (classId != null && !schoolClassRepo.existsById(classId)) {
            throw new IllegalStateException("Class not found with ID: " + classId);
        }
    }
}