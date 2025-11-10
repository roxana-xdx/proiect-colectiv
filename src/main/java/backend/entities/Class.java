package backend.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
@Entity
@Table(name="Class")
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long class_id;
    public Long getId() {
        return class_id;
    }
    @Column(name="class_name",unique=true,nullable=false)
    @NotBlank(message="Class Name should not be Empty")
    private String class_name;
    public String get_class_name() {
        return class_name;
    }
    public void set_class_name(String class_name) {
        this.class_name = class_name;
    }
    //TO DO: de de-comentat cand avem toate entitatile
    //  @ManyToOne(fetch=FetchType.LAZY)
    //  @JoinColumn(name="id", insertable=false, updatable=false)
    @Column(name="homeroom_teacher_id", nullable=false)
    @NotBlank
    private Long homeroom_teacher_id;
    public Long get_homeroom_teacher_id() {
        return homeroom_teacher_id;
    }
    public void set_homeroom_teacher_id(Long homeroom_teacher_id) {
        this.homeroom_teacher_id = homeroom_teacher_id;
    }

    public Class() {
    }

    public Class(String class_name, Long homeroom_teacher_id) {
        this.class_name = class_name;
        this.homeroom_teacher_id = homeroom_teacher_id;
    }

    @Override
    public String toString() {
        return "Class{" +
                "id=" + class_id +
                ", teacher id='" + homeroom_teacher_id + ' ' +
                '}';
    }
}
