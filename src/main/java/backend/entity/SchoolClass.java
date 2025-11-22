package backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "classes")
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long classId;

    @Column(name = "class_name", unique = true, nullable = false)
    @NotBlank(message = "Class Name should not be Empty")
    private String className;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "homeroom_teacher_id", referencedColumnName = "id", nullable = false)
    private Teacher homeroomTeacher;

    @Column(name = "homeroom_teacher_id", insertable = false, updatable = false)
    private Long homeroomTeacherId;

    @OneToMany(mappedBy = "clasa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pupil> pupils = new ArrayList<>();

    public SchoolClass() { }

    public SchoolClass(String className, Teacher homeroomTeacher) {
        this.className = className;
        this.homeroomTeacher = homeroomTeacher;
    }

    public Long getClassId() { return classId; }
    public void setClassId(Long classId) { this.classId = classId; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public Teacher getHomeroomTeacher() { return homeroomTeacher; }
    public void setHomeroomTeacher(Teacher homeroomTeacher) { this.homeroomTeacher = homeroomTeacher; }

    public Long getHomeroomTeacherId() { return homeroomTeacherId; }
    public void setHomeroomTeacherId(Long homeroomTeacherId) { this.homeroomTeacherId = homeroomTeacherId; }

    public List<Pupil> getPupils() { return pupils; }
    public void setPupils(List<Pupil> pupils) { this.pupils = pupils; }

    @Override
    public String toString() {
        return "SchoolClass{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", homeroomTeacherId=" + homeroomTeacherId +
                '}';
    }
}