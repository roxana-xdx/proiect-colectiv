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

    // Relatia N:1 cu Admin (Proprietarul Coloanei FK)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", referencedColumnName = "id", nullable = false)
    private Admin admin;
    // Am eliminat: @Column(name = "admin_id", nullable = false) private Long adminId;

    // Relatia N:1 cu SchoolClass (Proprietarul Coloanei FK)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "class_id", referencedColumnName = "class_id", nullable = false)
    private SchoolClass schoolClass;
    // Am eliminat: @Column(name = "class_id", nullable = false) private Long classId;

    @Column(nullable = false, length = 255)
    private String message;

    @Column(nullable = false)
    private LocalDate date;

    public ClassAnnouncement() {
    }

    // Constructor simplificat pentru Service (NU mai necesită adminId/classId)
    public ClassAnnouncement(Admin admin, SchoolClass schoolClass, String message, LocalDate date) {
        this.admin = admin;
        this.schoolClass = schoolClass;
        this.message = message;
        this.date = date;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Admin getAdmin() { return admin; }
    public void setAdmin(Admin admin) { this.admin = admin; }
    public SchoolClass getSchoolClass() { return schoolClass; }
    public void setSchoolClass(SchoolClass schoolClass) { this.schoolClass = schoolClass; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    // NOU: Metodă pentru a obține ID-ul din DTO (când ai nevoie de el)
    public Long getAdminId() {
        return admin != null ? admin.getId() : null;
    }
    public Long getClassId() {
        return schoolClass != null ? schoolClass.getClassId() : null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClassAnnouncement that = (ClassAnnouncement) o;
        return Objects.equals(message, that.message) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, date);
    }

    @Override
    public String toString() {
        return "ClassAnnouncement{" +
                "message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}