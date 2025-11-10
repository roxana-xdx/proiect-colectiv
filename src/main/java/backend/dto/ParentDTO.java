package backend.dto;


import org.antlr.v4.runtime.misc.NotNull;

public class ParentDTO {
    private Long id;
    @NotNull
    private String email;

    public ParentDTO(Long id, String email){
        this.id = id;
        this.email = email;
    }

    public ParentDTO() {}

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
