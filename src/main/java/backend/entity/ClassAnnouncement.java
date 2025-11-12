package backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "class_announcements")
public class ClassAnnouncement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    // Temporar inlocuim ClassEntity cu String pentru clasa
    // @ManyToOne
    // @JoinColumn(name = "class_id", nullable = false)
    // private ClassEntity classEntity;

    @Column(name = "class_id", nullable = false)
    private Long classId;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDate date;

    public ClassAnnouncement() {
    }

    // Constructor actualizat fara ClassEntity
    public ClassAnnouncement(Admin admin, Long classId, String message, LocalDate date) {
        this.admin = admin;
        this.classId = classId;
        this.message = message;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    // Getteri si setteri pentru classId in loc de ClassEntity
    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    // Metode comentate temporar pentru ClassEntity
    /*
    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }
    */

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassAnnouncement)) return false;
        ClassAnnouncement that = (ClassAnnouncement) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ClassAnnouncement{" +
                "id=" + id +
                ", admin=" + (admin != null ? admin.getId() : null) +
                ", classId=" + classId +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}