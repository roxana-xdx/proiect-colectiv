package backend.entity;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "pupil")
public class Pupil implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "class_id", referencedColumnName = "class_id", nullable = true)
    private Clasa clasa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parent_id", referencedColumnName = "id", nullable = true)
    private Parent parent;

//    @Column(name = "parent_id", nullable = false)
//    private Long parent_id;

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

    public Clasa getClasa() {
        return clasa;
    }

    public void setClasa(Clasa clasa) {
        this.clasa = clasa;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pupil pupil = (Pupil) o;
        return Objects.equals(id, pupil.id) && Objects.equals(clasa.getId(), pupil.getClasa()) && Objects.equals(parent.getId(), pupil.getParent().getId()) && Objects.equals(email, pupil.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clasa.getId(), parent.getId(), email);
    }

    @Override
    public String toString() {
        return "Pupil{" +
                "id=" + id +
                ", class_id=" + clasa.getId() +
                ", parent_id=" + parent.getId() +
                ", email='" + email + '\'' +
                '}';
    }
}