package backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "teachers")
public class Teacher implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "email", referencedColumnName = "email", nullable = false, unique = true)
    private User user;

    @Email(message = "Email should be valid")
    @Column(name = "email", unique = true, insertable = false, updatable = false, nullable = false, length = 255)
    private String email;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SchoolClass> classes = new ArrayList<>();

    //@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    //private List<Pupil_Teacher_Feedback> feedback = new ArrayList<>();

    public Teacher() {
    }

    public Teacher(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Teacher(User user) {
        this.user = user;
    }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public List<Clasa> getClasses() { return classes; }
//
//    public void setClasses(List<Clasa> classes) { this.classes = classes; }

    @Override
    public String toString() {
        return "Pupil{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(id, teacher.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}