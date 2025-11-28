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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", referencedColumnName = "id", nullable = false)
    private Admin admin;

    @Column(name = "admin_id", insertable = false, updatable = false, nullable = false)
    private Long adminId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "class_id", referencedColumnName = "class_id", nullable = false)
    private SchoolClass schoolClass;

    @Column(name = "class_id", insertable = false, updatable = false, nullable = false)
    private Long classId;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDate date;

    public ClassAnnouncement() {
    }

    public ClassAnnouncement(Long id, Long adminId, Long classId, String message, LocalDate date) {
        this.id = id;
        this.adminId = adminId;
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

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
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