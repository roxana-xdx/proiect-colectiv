package backend.repository;

import backend.entity.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface I_SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
    Optional<List<SchoolClass>> findByHomeroomTeacherId(Long homeroom_teacher_id);

    Optional<SchoolClass> findByClassName(String name);
}