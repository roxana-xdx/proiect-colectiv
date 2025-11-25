package backend.dto.teacher;

public class CreateTeacherRequest {
    private String email;

    public CreateTeacherRequest() {}

    public CreateTeacherRequest(String email) { this.email = email; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}