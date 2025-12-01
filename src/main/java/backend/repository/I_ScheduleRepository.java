package backend.repository;

import backend.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface I_ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByClassEntity_ClassId(long classId);

    List<Schedule> findByTeacher_Id(long teacherId);

}