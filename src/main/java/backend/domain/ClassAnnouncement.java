package backend.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Class_Announcement")
public class ClassAnnouncement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private ClassEntity classEntity;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDate date;

    public ClassAnnouncement() {
    }

    public ClassAnnouncement(Admin admin, ClassEntity classEntity, String message, LocalDate date) {
        this.admin = admin;
        this.classEntity = classEntity;
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

    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

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
    public String toString() {
        return "ClassAnnouncement{" +
                "id=" + id +
                ", admin=" + admin.getId() +
                ", classEntity=" + classEntity.getId() +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}