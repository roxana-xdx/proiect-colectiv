package backend.entity.validation;

import backend.entity.Admin;
import backend.repository.I_AdminRepository;
import java.time.LocalDate;

/**
 * Validator for ClassAnnouncement.
 * Contains validation logic for class announcements.
 */
public final class ClassAnnouncementValidator {

    private ClassAnnouncementValidator() { /* utility class */ }

    /**
     * Validate requirements for creating a ClassAnnouncement.
     * - admin_id non-null, exists in repository
     * - class_id non-null
     * - message non-null, not empty
     * - date non-null, not in the past
     */
    public static void validateCreate(Long adminId, Long classId, String message, LocalDate date, I_AdminRepository adminRepo) {
        // Admin validation
        if (adminId == null) {
            throw new IllegalArgumentException("Admin ID cannot be null");
        }

        if (!adminRepo.existsById(adminId)) {
            throw new IllegalStateException("Admin not found with ID: " + adminId);
        }

        // Class ID validation
        if (classId == null) {
            throw new IllegalArgumentException("Class ID cannot be null");
        }

        // Message validation
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }

        if (message.length() > 255) {
            throw new IllegalArgumentException("Message cannot exceed 255 characters");
        }

        // Date validation
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        LocalDate today = LocalDate.now();
        if (date.isBefore(today)) {
            throw new IllegalArgumentException("Date cannot be in the past");
        }
    }

    /**
     * Validate requirements for updating a ClassAnnouncement.
     * Only the message and date can be updated.
     */
    public static void validateUpdate(String message, LocalDate date) {
        // Message validation (if provided)
        if (message != null) {
            if (message.trim().isEmpty()) {
                throw new IllegalArgumentException("Message cannot be empty");
            }

            if (message.length() > 255) {
                throw new IllegalArgumentException("Message cannot exceed 255 characters");
            }
        }

        // Date validation (if provided)
        if (date != null) {
            LocalDate today = LocalDate.now();
            if (date.isBefore(today)) {
                throw new IllegalArgumentException("Date cannot be in the past");
            }
        }
    }
}