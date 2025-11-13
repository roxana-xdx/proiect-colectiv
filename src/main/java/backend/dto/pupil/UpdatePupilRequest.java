package backend.dto.pupil;

public class UpdatePupilRequest {
    private Long class_id;
    private Long parent_id;

    public UpdatePupilRequest(){}

    public UpdatePupilRequest(Long class_id, Long parent_id) {
        this.class_id = class_id;
        this.parent_id = parent_id;
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
}
