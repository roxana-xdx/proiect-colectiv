package backend.repository;

import backend.entity.ClassAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface I_ClassAnnouncementRepository extends JpaRepository<ClassAnnouncement, Long> {

    List<ClassAnnouncement> findBySchoolClass_ClassId(Long classId);

    List<ClassAnnouncement> findByAdmin_Id(Long adminId);
}