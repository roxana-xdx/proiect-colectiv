package backend.dto.schoolclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateSchoolClassRequest {
    @JsonProperty("class_name")
    private String name;
    @JsonProperty("homeroom_teacher_id")
    private Long homeroom_teacher_id;

    public CreateSchoolClassRequest() {}

    public CreateSchoolClassRequest(String name, Long homeroom_teacher_id) {
        this.name = name;
        this.homeroom_teacher_id = homeroom_teacher_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getHomeroom_teacher_id() {
        return homeroom_teacher_id;
    }

    public void setHomeroom_teacher_id(Long homeroom_teacher_id) {
        this.homeroom_teacher_id = homeroom_teacher_id;
    }
}