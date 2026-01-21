package backend.dto.admin;

public class CreateAdminRequest {
    private String email;

    public CreateAdminRequest() {}

    public CreateAdminRequest(String email) { this.email = email; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}