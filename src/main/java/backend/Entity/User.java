package backend.Entity;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "User")
public class User implements Serializable {

    @Id
    @Column(unique=true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    public enum Type {
        admin,
        teacher,
        parent,
        pupil
    }

    public User() {
    }

    public User(String email, String password, String name, Type type) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.type = type;
    }

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "email", referencedColumnName = "email", insertable = false, updatable = false)
//    private Teacher teacher;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "email", referencedColumnName = "email", insertable = false, updatable = false)
//    private Parent parent;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "email", referencedColumnName = "email", insertable = false, updatable = false)
//    private Admin admin;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "email", referencedColumnName = "email", insertable = false, updatable = false)
//    private Pupil Pupil;

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public String getName(){
        return this.name;
    }

    public Type getType(){
        return this.type;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}

