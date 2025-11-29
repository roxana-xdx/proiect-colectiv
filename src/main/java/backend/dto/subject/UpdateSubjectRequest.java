package backend.dto.subject;

import jakarta.validation.constraints.Size;

/**
 * Request DTO for updating an existing Subject.
 * All fields are optional to allow partial updates.
 */
public class UpdateSubjectRequest {

    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    private String name;

    // Constructor implicit (necesar pentru Jackson)
    public UpdateSubjectRequest() {}

    // Constructor complet
    public UpdateSubjectRequest(String name) {
        this.name = name;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}