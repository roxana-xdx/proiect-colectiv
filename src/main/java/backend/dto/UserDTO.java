package backend.dto;

import backend.entity.User;

public class UserDTO {
    private String email;
    private String name;
    private User.Type type;

    public UserDTO() {}

    public UserDTO(String email, String name, User.Type type) {
        this.email = email;
        this.name = name;
        this.type = type;
    }

    public static UserDTO fromEntity(User u) {
        return new UserDTO(u.getEmail(), u.getName(), u.getType());
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public User.Type getType() { return type; }
    public void setType(User.Type type) { this.type = type; }
}
