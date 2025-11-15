package backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "parents")
public class Parent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //am adaugat ca sa se genereze automat id-ul
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="email",referencedColumnName = "email", nullable = false, unique = true)
    private User user;

    @Column(name = "email", nullable = false, unique = true, length = 255, insertable = false, updatable = false)
    private String email;

    //am adaugat pentru a lega tabelele Pupil si Parent
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pupil> pupils = new ArrayList<>();

    public Parent() { }

    public Parent(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    //am adaugat setteri si getteri pentru a accesa copiii parintelui
    public List<Pupil> getPupils() {
        return pupils;
    }

    public void setPupils(List<Pupil> pupils) {
        this.pupils = pupils;
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
