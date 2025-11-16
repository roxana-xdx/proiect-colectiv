package backend.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "pupil")
public class Pupil implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", referencedColumnName = "class_id")
    private Clasa clasa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Parent parent;

//    @Column(name = "parent_id", nullable = false)
//    private Long parent_id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "email", referencedColumnName = "email", nullable = false, unique = true)
    private User user;

    @Column(name = "email", insertable = false, updatable = false, nullable = false, length = 255)
    private String email;

    public Pupil() {
    }

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
        return Objects.equals(id, pupil.id) && Objects.equals(clasa.getClassId(), pupil.getClasa()) && Objects.equals(parent.getId(), pupil.getParent().getId()) && Objects.equals(email, pupil.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clasa.getClassId(), parent.getId(), email);
    }

    @Override
    public String toString() {
        return "Pupil{" +
                "id=" + id +
                ", class_id=" + clasa.getClassId() +
                ", parent_id=" + parent.getId() +
                ", email='" + email + '\'' +
                '}';
    }
}