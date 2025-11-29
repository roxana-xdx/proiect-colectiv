package backend.entity.validation;

import backend.repository.I_TeacherRepository;
import backend.repository.I_SubjectRepository;
import backend.repository.I_SchoolClassRepository;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Validator for Schedule.
 * Contains validation logic for class schedules.
 */
public final class ScheduleValidator {

    private ScheduleValidator() { /* utility class */ }

    /**
     * Validate requirements for creating a Schedule.
     * - teacher_id non-null, exists in repository
     * - subject_id non-null, exists in repository
     * - class_id non-null, exists in repository
     * - date non-null, not in the past
     * - start_hour non-null
     * - end_hour non-null
     * - end_hour must be after start_hour
     */
    public static void validateCreate(
            Long teacherId,
            Long subjectId,
            Long classId,
            LocalDate date,
            LocalTime startHour,
            LocalTime endHour,
            I_TeacherRepository teacherRepo,
            I_SubjectRepository subjectRepo,
            I_SchoolClassRepository schoolClassRepo) {

        // Teacher validation
        if (teacherId == null) {
            throw new IllegalArgumentException("Teacher ID cannot be null");
        }
        if (!teacherRepo.existsById(teacherId)) {
            throw new IllegalStateException("Teacher not found with ID: " + teacherId);
        }

        // Subject validation
        if (subjectId == null) {
            throw new IllegalArgumentException("Subject ID cannot be null");
        }
        if (!subjectRepo.existsById(subjectId)) {
            throw new IllegalStateException("Subject not found with ID: " + subjectId);
        }

        // Class validation
        if (classId == null) {
            throw new IllegalArgumentException("Class ID cannot be null");
        }
        if (!schoolClassRepo.existsById(classId)) {
            throw new IllegalStateException("Class not found with ID: " + classId);
        }

        // Date validation
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        LocalDate today = LocalDate.now();
        if (date.isBefore(today)) {
            throw new IllegalArgumentException("Date cannot be in the past");
        }

        // Start hour validation
        if (startHour == null) {
            throw new IllegalArgumentException("Start hour cannot be null");
        }

        // End hour validation
        if (endHour == null) {
            throw new IllegalArgumentException("End hour cannot be null");
        }

        // Time logic validation
        if (!endHour.isAfter(startHour)) {
            throw new IllegalArgumentException("End hour must be after start hour");
        }
    }

    /**
     * Validate requirements for updating a Schedule.
     * All fields are optional for partial update.
     */
    public static void validateUpdate(
            Long teacherId,
            Long subjectId,
            Long classId,
            LocalDate date,
            LocalTime startHour,
            LocalTime endHour,
            I_TeacherRepository teacherRepo,
            I_SubjectRepository subjectRepo,
            I_SchoolClassRepository schoolClassRepo) {

        // Teacher validation (if provided)
        if (teacherId != null && !teacherRepo.existsById(teacherId)) {
            throw new IllegalStateException("Teacher not found with ID: " + teacherId);
        }

        // Subject validation (if provided)
        if (subjectId != null && !subjectRepo.existsById(subjectId)) {
            throw new IllegalStateException("Subject not found with ID: " + subjectId);
        }

        // Class validation (if provided)
        if (classId != null && !schoolClassRepo.existsById(classId)) {
            throw new IllegalStateException("Class not found with ID: " + classId);
        }

        // Date validation (if provided)
        if (date != null) {
            LocalDate today = LocalDate.now();
            if (date.isBefore(today)) {
                throw new IllegalArgumentException("Date cannot be in the past");
            }
        }

        // Time logic validation (if both provided)
        if (startHour != null && endHour != null) {
            if (!endHour.isAfter(startHour)) {
                throw new IllegalArgumentException("End hour must be after start hour");
            }
        }
    }
}