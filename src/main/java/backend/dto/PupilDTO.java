package backend.dto;

import backend.entity.Pupil;
import backend.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PupilDTO {
    private Long id;
    private Long classId;
    private Long parentId;

    @NotNull
    @Email
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    public PupilDTO() {}

    public PupilDTO(Long id, Long classId, Long parentId, String email, String name) {
        this.id = id;
        this.classId = classId;
        this.parentId = parentId;
        this.email = email;
        this.name = name;
    }

    public static PupilDTO toDTO(Pupil pupil) {
        if (pupil == null) return null;
        User user = pupil.getUser();
        Long classId = (pupil.getClasa() != null) ? pupil.getClasa().getClassId() : null;
        Long parentId = (pupil.getParent() != null) ? pupil.getParent().getId() : null;
        String email = (user != null) ? user.getEmail() : pupil.getEmail();
        String name = (user != null) ? user.getName() : null;
        return new PupilDTO(pupil.getId(), classId, parentId, email, name);
    }

    public Pupil toEntity() {
        Pupil pupil = new Pupil();
        if (this.id != null) pupil.setId(this.id);
        return pupil;
    }

    public Pupil toEntityWithUser(User user) {
        Pupil pupil = new Pupil();
        if (this.id != null) pupil.setId(this.id);
        pupil.setUser(user);
        return pupil;
    }

    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @JsonProperty("class_id")  // JSON snake_case
    public Long getClassId() { return classId; }
    public void setClassId(Long classId) { this.classId = classId; }

    @JsonProperty("parent_id")  // JSON snake_case
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}