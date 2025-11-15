package backend.dto;


import backend.entity.Admin;
import backend.entity.Parent;
import backend.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ParentDTO {
    @NotNull
    private Long id;
    
    @NotNull
    @Email
    @Size(max=255)
    private String email;

    public ParentDTO() {}

    public ParentDTO(Long id, String email){
        this.id = id;
        this.email = email;
    }

    public static ParentDTO fromEntity(Parent parent) {
        if (parent == null) return null;
        User user = parent.getUser();
        return new ParentDTO(
                parent.getId(),
                user != null ? user.getEmail() : null
        );
    }

    public Parent toEntity() {
        Parent parent = new Parent();
        if (this.id != null) {
            parent.setId(this.id);
        }
        return parent;
    }

    public Parent toEntityWithUser(User user) {
        Parent parent = new Parent();
        if (this.id != null) {
            parent.setId(this.id);
        }
        parent.setUser(user);
        return parent;
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
}
