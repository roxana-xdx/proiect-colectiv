package backend.dto.clasa;

public class CreateClassRequest {
    private String name;
    private Long homeroom_teacher_id;

    public CreateClassRequest() {}

    public CreateClassRequest(String name, Long homeroom_teacher_id) {
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
