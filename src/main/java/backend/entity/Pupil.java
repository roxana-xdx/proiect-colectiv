package backend.entity;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.Objects;


@Entity
@Table(name = "pupil")
public class Pupil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToMany(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "class_id", referencedColumnName = "class_id", nullable = false, unique = true )
//    private Class class;

    @Column(name = "class_id", nullable = false)
    private Long class_id;

//    @OneToMany(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "parent_id", referencedColumnName = "parent_id", nullable = false, unique = true )
//    private Parent parent;

    @Column(name = "parent_id", nullable = false)
    private Long parent_id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "email", referencedColumnName = "email", nullable = false, unique = true)
    private User user;

    @Column(name = "email", insertable = false, updatable = false, nullable = false, length = 255)
    private String email;

    ///TODO: reintrodu codul dupa ce ai si clasele Parent, Class, User si Pupil_Teacher_Feedback
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "class_id", insertable = false, updatable = false)
//    private Class classEntity;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
//    private Parent parent;

    //un singur mail pt user care e si copil

//
//    @OneToMany(mappedBy = "pupil", cascade = CascadeType.ALL)
//    private List<Pupil_teacher_Feedback> feedbacks = new ArrayList<>();

    public Pupil() {
    }

//    public Pupil(Long id, Long class_id, Long parent_id, String email) {
//        this.id = id;
//        this.class_id = class_id;
//        this.parent_id = parent_id;
//        this.email = email;
//    }

    public Pupil(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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