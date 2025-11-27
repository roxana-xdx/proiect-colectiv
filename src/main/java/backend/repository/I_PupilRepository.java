package backend.Repository;

import backend.entity.Pupil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface I_PupilRepository extends JpaRepository<Pupil, Long> {
    Optional<Pupil> findByEmail(String email);
    Optional<Pupil> findByUser_Email(String email);

    List<Pupil> findByClasa_ClassId(Long classId);
}