package backend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users") // avoid reserved word "User"
public class User implements Serializable {

    @Id
    @Column(name = "email", nullable = false, length = 255)
    private String email; // no validation annotation here per your request

    @Column(name = "password", nullable = false, length = 255)
    private String password; // plain-text for now (you decided no hashing)

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 32)
    private Type type;

    public enum Type {
        ADMIN,
        TEACHER,
        PARENT,
        PUPIL
    }

    public User() { }

    public User(String email, String password, String name, Type type) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.type = type;
    }

    // Getters
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public Type getType() { return type; }

    // Setters
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setName(String name) { this.name = name; }
    public void setType(Type type) { this.type = type; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        // intentionally do NOT include password
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}