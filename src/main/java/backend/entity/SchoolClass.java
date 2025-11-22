package backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "classes")
public class SchoolClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long class_id;

    public SchoolClass(Long id, String className, Long homeroomTeacherId) {
        this.class_id = id;
        this.className = className;
        this.homeroomTeacherId = homeroomTeacherId;
    }

    public Long getClassId() {
        return class_id;
    }

    public void setClassId(Long class_id) {
        this.class_id = class_id;
    }

    @Column(name = "class_name", unique = true, nullable = false)
    @NotBlank(message = "Class Name should not be Empty")
    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String class_name) {
        this.className = class_name;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id", insertable=false, updatable=false)
    private Teacher teacher;

    @Column(name = "homeroom_teacher_id", nullable = false)
    @NotNull
    private Long homeroomTeacherId;

    public Long getHomeroomTeacherId() {
        return homeroomTeacherId;
    }

    public void setHomeroomTeacherId(Long homeroom_teacher_id) {
        this.homeroomTeacherId = homeroom_teacher_id;
    }

    @OneToMany(mappedBy = "clasa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pupil> pupils = new ArrayList<>();

    public List<Pupil> getPupils() {
        return pupils;
    }

    public void setPupils(List<Pupil> pupils) {
        this.pupils = pupils;
    }

    public SchoolClass() {
    }

    public SchoolClass(String className, Long homeroom_teacher_id) {
        this.className = className;
        this.homeroomTeacherId = homeroom_teacher_id;
    }

    @Override
    public String toString() {
        return "Class{" +
                "id=" + class_id +
                ", teacher id='" + homeroomTeacherId + ' ' +
                '}';
    }
}