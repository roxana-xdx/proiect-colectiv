package backend.entity.validation;

/**
 * Validator for Subject.
 * Contains validation logic for subjects.
 */
public final class SubjectValidator {

    private SubjectValidator() { /* utility class */ }

    /**
     * Validate requirements for creating a Subject.
     * - name non-null, not empty
     * - name length within limits
     */
    public static void validateCreate(String name) {
        // Name validation
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (name.length() > 255) {
            throw new IllegalArgumentException("Name cannot exceed 255 characters");
        }
    }

    /**
     * Validate requirements for updating a Subject.
     * Only the name can be updated.
     */
    public static void validateUpdate(String name) {
        // Name validation (if provided)
        if (name != null) {
            if (name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty");
            }

            if (name.length() > 255) {
                throw new IllegalArgumentException("Name cannot exceed 255 characters");
            }
        }
    }
}