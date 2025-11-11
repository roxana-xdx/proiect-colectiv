package backend.dto;

import backend.entity.Pupil;
import jakarta.validation.constraints.NotNull;

public class PupilDTO {
    private Long id;
    @NotNull
    private Long class_id;
    @NotNull
    private Long parent_id;
    @NotNull
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
        var user = pupil.getUser();
        return new PupilDTO(pupil.getId(), pupil.getClass_id(), pupil.getParent_id(), user != null ? user.getEmail() : null);
    }

    public static Pupil toEntity(PupilDTO pupilDTO) {
        return new Pupil(pupilDTO.getId(), pupilDTO.getClass_id(), pupilDTO.getParent_id(), pupilDTO.getEmail());
    }
}
