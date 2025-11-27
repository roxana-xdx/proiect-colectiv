package backend.Repository;

import backend.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface I_SheduleRepo extends JpaRepository<Schedule, Long> {
    List<Schedule> findByClass_id(long classId);
    List<Schedule> findByTeacher_id(long teacherId);
    Optional<Schedule> findById_andDate(long id, LocalDate date);
}
