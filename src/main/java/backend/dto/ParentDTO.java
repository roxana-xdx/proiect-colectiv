package backend.dto;


import backend.entity.Parent;

public class ParentDTO {
    private Long id;
    private String email;

    public ParentDTO(Long id, String email){
        this.id = id;
        this.email = email;
    }

    public static ParentDTO fromEntity(Parent p) {
        return new ParentDTO(p.getId(), p.getEmail());
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
