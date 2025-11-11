package backend.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
@Entity
@Table(name="Clasa")
public Class Clasa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Clasa_id;
    public Long getId() {
        return Clasa_id;
    }
    @Column(name="Clasa_name",unique=true,nullable=false)
    @NotBlank(message="Clasa Name should not be Empty")
    private String Clasa_name;
    public String get_Clasa_name() {
        return Clasa_name;
    }
    public void set_Clasa_name(String Clasa_name) {
        this.Clasa_name = Clasa_name;
    }
    //TO DO: de de-comentat cand avem toate entitatile
    //  @ManyToOne(fetch=FetchType.LAZY)
    //  @JoinColumn(name="id", insertable=false, updatable=false)
    @Column(name="homeroom_teacher_id", nullable=false)
    @NotBlank
    private Long homeroom_teacher_id;
    public Long get_homeroom_teacher_id() {
        return homeroom_teacher_id;
    }
    public void set_homeroom_teacher_id(Long homeroom_teacher_id) {
        this.homeroom_teacher_id = homeroom_teacher_id;
    }

    public Clasa() {
    }

    public Clasa(String Clasa_name, Long homeroom_teacher_id) {
        this.Clasa_name = Clasa_name;
        this.homeroom_teacher_id = homeroom_teacher_id;
    }

    @Override
    public String toString() {
        return "Clasa{" +
                "id=" + Clasa_id +
                ", teacher id='" + homeroom_teacher_id + ' ' +
                '}';
    }
}
