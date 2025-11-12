package backend.dto;

import backend.entity.Admin;
import backend.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for Admin.
 * Mirrors the style of your ExpenseDTO: validation annotations, all-args and no-args constructors,
 * simple getters/setters and helper conversion methods.
 *
 * Note: Admin table does not store the name — name is taken from the linked User.
 */
public class AdminDTO {

    private Long id;

    @NotNull
    @Email
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    /**
     * All-args constructor.
     *
     * @param id    admin id (nullable for create)
     * @param email linked user email
     * @param name  linked user name
     */
    public AdminDTO(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public AdminDTO() {}

    /**
     * Build DTO from Admin entity. Reads the User relation for email/name if present.
     *
     * @param admin entity
     * @return AdminDTO or null if admin is null
     */
    public static AdminDTO fromEntity(Admin admin) {
        if (admin == null) return null;
        User user = admin.getUser();
        return new AdminDTO(
                admin.getId(),
                user != null ? user.getEmail() : null,
                user != null ? user.getName() : null
        );
    }

    /**
     * Convert DTO to entity without setting the User relation.
     * Service should fetch the User and set it on the returned Admin (admin.setUser(user)) before saving.
     *
     * @return Admin entity with id possibly set; user NOT set
     */
    public Admin toEntity() {
        Admin admin = new Admin();
        if (this.id != null) {
            admin.setId(this.id);
        }
        // do not set admin.setUser(...) here — service must provide managed User
        return admin;
    }

    /**
     * Convert DTO to entity and set provided managed User.
     *
     * @param user managed User entity to link
     * @return Admin entity ready to persist
     */
    public Admin toEntityWithUser(User user) {
        Admin admin = new Admin();
        if (this.id != null) {
            admin.setId(this.id);
        }
        admin.setUser(user);
        return admin;
    }

    // --- getters / setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Email is present on DTO for validation/transport purposes. Persistence should use the managed User entity.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    /**
     * Name comes from linked User; setter is available for DTO population but service should not rely on it for persistence.
     */
    public void setName(String name) {
        this.name = name;
    }
}