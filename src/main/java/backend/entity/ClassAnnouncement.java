package backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "announcements")
public class ClassAnnouncement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relatia N:1 cu Admin (cine a postat)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false) // Modificat insertable/updatable aici
    private Admin admin;

    @Column(name = "admin_id", nullable = false) // Am eliminat insertable/updatable de aici
    private Long adminId;

    // Relatia N:1 cu SchoolClass (pentru ce clasa este)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "class_id", referencedColumnName = "class_id", nullable = false, insertable = false, updatable = false) // Modificat insertable/updatable aici
    private SchoolClass schoolClass;

    @Column(name = "class_id", nullable = false) // Am eliminat insertable/updatable de aici
    private Long classId;

    @Column(nullable = false, length = 255)
    private String message;

    @Column(nullable = false)
    private LocalDate date;

    public ClassAnnouncement() {
    }

    // Constructor simplificat pentru Service
    public ClassAnnouncement(Admin admin, SchoolClass schoolClass, String message, LocalDate date) {
        this.admin = admin;
        this.schoolClass = schoolClass;
        this.message = message;
        this.date = date;
    }

    // Getters and Setters (Rămân similare, ajustate pentru consistență)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Admin getAdmin() { return admin; }
    public void setAdmin(Admin admin) {
        this.admin = admin;
        if (admin != null) {
            this.adminId = admin.getId();
        }
    }
    public Long getAdminId() { return adminId; }
    public void setAdminId(Long adminId) { this.adminId = adminId; }
    public SchoolClass getSchoolClass() { return schoolClass; }
    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
        if (schoolClass != null) {
            this.classId = schoolClass.getClassId();
        }
    }
    public Long getClassId() { return classId; }
    public void setClassId(Long classId) { this.classId = classId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

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
                ", adminId=" + adminId +
                ", classId=" + classId +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}