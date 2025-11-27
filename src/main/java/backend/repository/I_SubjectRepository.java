package backend.Repository;

import backend.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface I_SubjectRepository extends JpaRepository<Subject, Long> {

}
