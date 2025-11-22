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

    @OneToMany(mappedBy = "homeroomTeacher", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SchoolClass> classes = new ArrayList<>();

    public Teacher() { }

    public Teacher(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Teacher(User user) {
        this.user = user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getEmail() { return email; }

    public List<SchoolClass> getClasses() { return classes; }
    public void setClasses(List<SchoolClass> classes) { this.classes = classes; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher t = (Teacher) o;
        return id != null && id.equals(t.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Teacher{" + "id=" + id + ", email='" + email + '\'' + '}';
    }
}