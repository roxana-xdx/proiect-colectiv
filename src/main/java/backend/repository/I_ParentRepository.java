package backend.repository;

import backend.dto.ParentDTO;
import backend.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface I_ParentRepository extends JpaRepository<Parent, String> {
    Parent findByEmail(String email);
    boolean existsByEmail(String email);
}
