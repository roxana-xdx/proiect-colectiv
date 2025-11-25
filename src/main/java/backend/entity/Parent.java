package backend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "parents")
public class Parent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "email", referencedColumnName = "email", nullable = false, unique = true)
    private User user;

    @Column(name = "email", nullable = false, unique = true, length = 255, insertable = false, updatable = false)
    private String email;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pupil> pupils = new ArrayList<>();

    public Parent() { }

    public Parent(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getEmail() { return email; }

    public List<Pupil> getPupils() { return pupils; }
    public void setPupils(List<Pupil> pupils) { this.pupils = pupils; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parent)) return false;
        Parent p = (Parent) o;
        return id != null && id.equals(p.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Parent{" + "id=" + id + ", email=" + email + '}';
    }
}