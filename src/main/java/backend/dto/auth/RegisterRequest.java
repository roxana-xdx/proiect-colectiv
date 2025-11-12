package backend.dto.auth;

import backend.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class RegisterRequest {
    @Email
    @NotBlank
    @Size(max = 255)
    private String email;

    @NotBlank
    @Size(min = 8, max = 255)
    private String password;

    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    private User.Type type;

    public RegisterRequest() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public User.Type getType() { return type; }
    public void setType(User.Type type) { this.type = type; }
}