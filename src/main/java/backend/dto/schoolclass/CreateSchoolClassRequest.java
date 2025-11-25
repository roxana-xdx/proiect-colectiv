package backend.dto.schoolclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateSchoolClassRequest {

    @JsonProperty("class_name")
    @NotBlank
    @Size(max = 255)
    private String className;

    @JsonProperty("homeroom_teacher_id")
    @NotNull
    private Long homeroomTeacherId;

    public CreateSchoolClassRequest() {}

    public CreateSchoolClassRequest(String className, Long homeroomTeacherId) {
        this.className = className;
        this.homeroomTeacherId = homeroomTeacherId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Long getHomeroomTeacherId() {
        return homeroomTeacherId;
    }

    public void setHomeroomTeacherId(Long homeroomTeacherId) {
        this.homeroomTeacherId = homeroomTeacherId;
    }
}