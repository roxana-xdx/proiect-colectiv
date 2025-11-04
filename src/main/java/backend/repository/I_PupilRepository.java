package backend.repository;

import backend.entities.Pupil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface I_PupilRepository extends JpaRepository<Pupil,Long> {
    Optional<Pupil> findByEmail(String email);
    List<Pupil> findByClass_id(Long class_id);
}
