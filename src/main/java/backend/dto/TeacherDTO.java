package backend.dto;

import backend.entity.Teacher;
import backend.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TeacherDTO {

    private Long id;

    @NotBlank
    @Email
    @Size(min = 5, max = 255)
    private String email;

    @NotBlank
    @Size(max = 255)
    private String name;


    public TeacherDTO(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public TeacherDTO() {}

    public static TeacherDTO fromEntity(Teacher teacher) {
        if (teacher == null) return null;
        User user = teacher.getUser();
        return new TeacherDTO(
                teacher.getId(),
                user != null ? user.getEmail() : null,
                user != null ? user.getName() : null
        );
    }

    public Teacher toEntity() {
        Teacher teacher = new Teacher();
        if (this.id != null) {
            teacher.setId(this.id);
        }
        return teacher;
    }

    public Teacher toEntityWithUser(User user) {
        Teacher teacher = new Teacher();
        if (this.id != null) {
            teacher.setId(this.id);
        }
        teacher.setUser(user);
        return teacher;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}