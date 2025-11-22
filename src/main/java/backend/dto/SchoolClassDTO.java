package backend.dto;

import backend.entity.SchoolClass;

public class SchoolClassDTO {
    private String class_name;
    private Long homeroom_teacher_id;
    private Long class_id;

    public SchoolClassDTO() {
    }

    public SchoolClassDTO(Long class_id, String class_name, Long homeroom_teacher_id) {
        this.class_id = class_id;
        this.class_name = class_name;
        this.homeroom_teacher_id = homeroom_teacher_id;
    }

    public static SchoolClassDTO fromEntity(SchoolClass c) {
        return new SchoolClassDTO(c.getClassId(), c.getClassName(), c.getHomeroomTeacherId());
    }

    public SchoolClass toEntity() {
        SchoolClass c = new SchoolClass();
        if(this.class_id != null) {
            c.setClassId(this.class_id);
        }
        return c;
    }

    public String get_class_name() {
        return class_name;
    }

    public void set_class_name(String class_name) {
        this.class_name = class_name;
    }

    public Long get_homeroom_teacher_id() {
        return homeroom_teacher_id;
    }

    public void set_homeroom_teacher_id(Long homeroom_teacher_id) {
        this.homeroom_teacher_id = homeroom_teacher_id;
    }

    public Long get_class_id() {
        return class_id;
    }

    //not sure if this is supposed to be here, i used identity on the class ids
    public void set_class_id(Long class_id) {
        this.class_id = class_id;
    }

}