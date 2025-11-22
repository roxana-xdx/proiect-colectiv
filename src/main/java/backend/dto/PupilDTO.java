package backend.dto;

import backend.entity.Pupil;
import backend.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PupilDTO {
    private Long id;
    private Long class_id;
    private Long parent_id;
    @NotNull
    @Email
    @Size(max = 255)
    private String email;

    public PupilDTO(Long id, Long class_id, Long parent_id, String email) {
        this.id = id;
        this.class_id = class_id;
        this.parent_id = parent_id;
        this.email = email;
    }

    public PupilDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClass_id() {
        return class_id;
    }

    public void setClass_id(Long class_id) {
        this.class_id = class_id;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static PupilDTO toDTO(Pupil pupil) {
        if(pupil == null) return null;
        User user = pupil.getUser();
        return new PupilDTO(pupil.getId(), pupil.getClasa().getClassId(), pupil.getParent().getId(), user != null ? user.getEmail() : null);
    }

    public Pupil toEntity() {
        Pupil pupil = new Pupil();
        if(this.id != null) pupil.setId(this.id);
        return pupil;
    }

    public Pupil toEntityWithUser(User user) {
        Pupil pupil = new Pupil();
        if(this.id != null) pupil.setId(this.id);
        pupil.setUser(user);
        return pupil;
    }
}