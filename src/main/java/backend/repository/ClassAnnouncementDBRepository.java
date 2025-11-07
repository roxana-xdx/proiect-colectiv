package backend.repository;

import backend.domain.ClassAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClassAnnouncementDBRepository extends JpaRepository<ClassAnnouncement, Long> {

    List<ClassAnnouncement> findByClassEntityId(Long classId);

    List<ClassAnnouncement> findByAdminId(Long adminId);

    List<ClassAnnouncement> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<ClassAnnouncement> findByClassEntityIdAndDateAfter(Long classId, LocalDate date);

    List<ClassAnnouncement> findByMessageContaining(String text);

    List<ClassAnnouncement> findByClassEntityIdOrderByDateDesc(Long classId);
}