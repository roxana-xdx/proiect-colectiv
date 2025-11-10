package backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
        @Repository
public interface ClassRepo extends JpaRepository<Class,Long> {
            Optional<Class> findByTeacherID(int homeroom_teacher_id);
            Optional<Class> findByName(String name);
        }