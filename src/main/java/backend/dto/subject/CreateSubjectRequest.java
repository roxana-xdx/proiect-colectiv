package backend.dto.subject;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for creating a new Subject.
 * Does not include id (auto-generated).
 */
public class CreateSubjectRequest {

    @NotNull(message = "Name is mandatory")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    private String name;

    // Constructor implicit (necesar pentru Jackson)
    public CreateSubjectRequest() {}

    // Constructor complet
    public CreateSubjectRequest(String name) {
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