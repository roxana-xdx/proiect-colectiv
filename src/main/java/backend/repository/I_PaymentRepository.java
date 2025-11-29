package backend.repository;

import backend.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface I_ParentRepository extends JpaRepository<Parent, Long> {

    Optional<Parent> findByEmail(String email);

    boolean existsById(Long id);
}