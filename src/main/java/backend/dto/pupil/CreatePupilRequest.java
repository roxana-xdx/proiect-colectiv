package backend.dto.pupil;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public class CreatePupilRequest {
    @NotBlank
    @Email
    private String email;

    @JsonProperty("class_id")
    @NotNull
    private Long class_id;

    @JsonProperty("parent_id")
    @NotNull
    private Long parent_id;

    public CreatePupilRequest() {}

    public CreatePupilRequest(String email, Long class_id, Long parent_id) {
        this.email = email;
        this.class_id = class_id;
        this.parent_id = parent_id;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getClass_id() { return class_id; }
    public void setClass_id(Long class_id) { this.class_id = class_id; }

    public Long getParent_id() { return parent_id; }
    public void setParent_id(Long parent_id) { this.parent_id = parent_id; }
}