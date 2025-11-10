package backend.Repository;

import backend.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface I_Repo extends JpaRepository<Schedule, Long> {
    List<Schedule> findByClass_id(long classId);
    List<Schedule> findByTeacher_id(long teacherId);
    Optional<Schedule> findById_andDate(long id, LocalDate date);
}
