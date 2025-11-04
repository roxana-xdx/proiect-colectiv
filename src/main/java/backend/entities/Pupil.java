package backend.entities;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "pupil")
public class Pupil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "class_id", nullable = false)
    private Long class_id;

    @NonNull
    @Column(name = "parent_id", nullable = false)
    private Long parent_id;

    @NonNull
    @Column(unique=true, nullable = false)
    private String email;

    ///TODO: reintrodu codul dupa ce ai si clasele Parent, Class, User si Pupil_Teacher_Feedback
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "class_id", insertable = false, updatable = false)
//    private Class classEntity;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
//    private Parent parent;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "email", referencedColumnName = "email", insertable = false, updatable = false)
//    private User user;
//
//    @OneToMany(mappedBy = "pupil", cascade = CascadeType.ALL)
//    private List<Pupil_teacher_Feedback> feedbacks = new ArrayList<>();

    public Pupil() {
    }

    public Pupil(Long id, Long class_id, Long parent_id, String email) {
        this.id = id;
        this.class_id = class_id;
        this.parent_id = parent_id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public Class getClassEntity() {
//        return classEntity;
//    }
//
//    public void setClassEntity(Class classEntity) {
//        this.classEntity = classEntity;
//    }

//    public Parent getParent() {
//        return parent;
//    }
//
//    public void setParent(Parent parent) {
//        this.parent = parent;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public List<Pupil_Teacher_Feedback> getFeedback(){
//        return feedbacks;
//    }
//
//    public void setFeedbacks(List<PupilTeacherFeedback> feedbacks) {
//        this.feedbacks = feedbacks;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pupil pupil = (Pupil) o;
        return Objects.equals(id, pupil.id) && Objects.equals(class_id, pupil.class_id) && Objects.equals(parent_id, pupil.parent_id) && Objects.equals(email, pupil.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, class_id, parent_id, email);
    }

    @Override
    public String toString() {
        return "Pupil{" +
                "id=" + id +
                ", class_id=" + class_id +
                ", parent_id=" + parent_id +
                ", email='" + email + '\'' +
                '}';
    }
}