package backend.repository;
import backend.entities.Clasa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
        @Repository
public interface ClassRepo extends JpaRepository<Clasa,Long> {
            Optional<List<Clasa>> findClassByTeacherID(Long homeroom_teacher_id);
            Optional<List<Clasa>> findClassByName(String name);
        }