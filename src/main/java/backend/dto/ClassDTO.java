package backend.dto;

import backend.entity.Clasa;

public class ClassDTO {
    private String class_name;
    private Long homeroom_teacher_id;
    private Long class_id;

    public ClassDTO() {
    }

    public ClassDTO(Long class_id, String class_name, Long homeroom_teacher_id) {
        this.class_id = class_id;
        this.class_name = class_name;
        this.homeroom_teacher_id = homeroom_teacher_id;
    }

    public static ClassDTO fromEntity(Clasa c) {
        return new ClassDTO(c.getId(), c.get_class_name(), c.get_homeroom_teacher_id());
    }

    public Clasa toEntity() {
        Clasa c = new Clasa();
        if(this.class_id != null) {
            c.setId(this.class_id);
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
