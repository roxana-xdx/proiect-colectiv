package backend.dto;

import backend.entity.Subject;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for Subject.
 * Includes validation annotations, constructors,
 * getters/setters and helper conversion methods.
 */
public class SubjectDTO {

    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    /**
     * All-args constructor.
     */
    public SubjectDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * No-args constructor.
     */
    public SubjectDTO() {}

    /**
     * Build DTO from Subject entity.
     *
     * @param subject entity
     * @return SubjectDTO or null if subject is null
     */
    public static SubjectDTO fromEntity(Subject subject) {
        if (subject == null) return null;

        return new SubjectDTO(
                subject.getId(),
                subject.getName()
        );
    }

    /**
     * Convert DTO to entity.
     *
     * @return Subject entity
     */
    public Subject toEntity() {
        Subject subject = new Subject();
        if (this.id != null) {
            subject.setId(this.id);
        }
        subject.setName(this.name);
        return subject;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}