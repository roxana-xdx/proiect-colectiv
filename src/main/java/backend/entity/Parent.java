package backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "parent")
public class Parent implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="email",referencedColumnName = "email", nullable = false, unique = true)
    private User user;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    public Parent() { }

    public Parent(Long id, String email) {
        this.id = id;
        this.email = email;
    }

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Parent parent = (Parent) o;
        return id.equals(parent.id) && Objects.equals(email, parent.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Parent{" + "id=" + id + ", email=" + email + '}';
    }
}
