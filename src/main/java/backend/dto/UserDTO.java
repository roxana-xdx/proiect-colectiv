package backend.dto;

import backend.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for User entity.
 * Does not include password (sensitive). Use dedicated request DTOs for registration/update that include password.
 */
public class UserDTO {

    @NotNull
    @Email
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    private User.Type type;

    public UserDTO() {}

    /**
     * All-args constructor.
     *
     * @param email user email (PK)
     * @param name  user name
     * @param type  user type enum
     */
    public UserDTO(String email, String name, User.Type type) {
        this.email = email;
        this.name = name;
        this.type = type;
    }

    /**
     * Create DTO from entity.
     *
     * @param user entity
     * @return dto
     */
    public static UserDTO fromEntity(User user) {
        if (user == null) return null;
        return new UserDTO(user.getEmail(), user.getName(), user.getType());
    }

    /**
     * Convert DTO to entity. Note: password is NOT set here.
     * Use toEntityWithPassword(...) when you need to include the password (e.g. registration).
     *
     * @return User entity with email, name and type set
     */
    public User toEntity() {
        User u = new User();
        u.setEmail(this.email);
        u.setName(this.name);
        u.setType(this.type);
        return u;
    }

    /**
     * Convert DTO to entity and set password.
     *
     * @param password plain-text password to set
     * @return User entity with password set (use only when password is provided by request)
     */
    public User toEntityWithPassword(String password) {
        User u = toEntity();
        u.setPassword(password);
        return u;
    }

    // getters / setters

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

    public User.Type getType() {
        return type;
    }

    public void setType(User.Type type) {
        this.type = type;
    }
}