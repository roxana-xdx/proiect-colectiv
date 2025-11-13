package backend.dto.admin;

public class CreateParentRequest {
    private String email;

    public CreateParentRequest() {
    }

    public CreateParentRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
