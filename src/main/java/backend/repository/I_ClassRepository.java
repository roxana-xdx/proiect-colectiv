package backend.repository;

import backend.entity.Clasa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface I_ClassRepository extends JpaRepository<Clasa, Long> {
    Optional<List<Clasa>> findByHomeroomTeacherId(Long homeroom_teacher_id);

    Optional<Clasa> findByClassName(String name);
}