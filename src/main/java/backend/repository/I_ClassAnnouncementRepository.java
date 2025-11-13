package backend.repository;

import backend.entity.ClassAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface I_ClassAnnouncementRepository extends JpaRepository<ClassAnnouncement, Long> {

}