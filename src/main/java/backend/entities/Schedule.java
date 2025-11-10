package backend.entities;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "teacher_id", nullable = false)
    private Long teacher_d;

    @Column(name = "class_id", nullable = false)
    private Long class_id;

    @Column(name = "subject_id", nullable = false)
    private Long subject_i7d;
}
