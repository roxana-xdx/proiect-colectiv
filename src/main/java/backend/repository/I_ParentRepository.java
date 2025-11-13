package backend.repository;

import backend.dto.ParentDTO;
import backend.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface I_ParentRepository extends JpaRepository<Parent, String> {
    Optional<Parent> findByEmail(String email);
    boolean existsByEmail(String email);
}
