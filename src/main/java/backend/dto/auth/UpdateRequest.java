package backend.dto.auth;

import backend.entity.User;
import jakarta.validation.constraints.Size;


public class UpdateRequest {
    @Size(min = 8, max = 255)
    private String password;

    @Size(min = 1, max = 255)
    private String name;

    private User.Type type;

    public UpdateRequest() {}

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public User.Type getType() { return type; }
    public void setType(User.Type type) { this.type = type; }
}