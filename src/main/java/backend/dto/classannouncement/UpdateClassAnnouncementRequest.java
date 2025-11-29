package backend.dto.classannouncement;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Request DTO for updating an existing ClassAnnouncement.
 * All fields are optional to allow partial updates.
 * Note: adminId and classId typically should not be updated after creation.
 */
public class UpdateClassAnnouncementRequest {

    @Size(min = 1, max = 255, message = "Message must be between 1 and 255 characters")
    private String message;

    private LocalDate date;

    // Constructor implicit (necesar pentru Jackson)
    public UpdateClassAnnouncementRequest() {}

    // Constructor complet
    public UpdateClassAnnouncementRequest(String message, LocalDate date) {
        this.message = message;
        this.date = date;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}