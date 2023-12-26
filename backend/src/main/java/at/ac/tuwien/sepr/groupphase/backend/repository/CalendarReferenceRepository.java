package at.ac.tuwien.sepr.groupphase.backend.repository;

import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CalendarReferenceRepository extends JpaRepository<CalendarReference, Long> {
    CalendarReference findCalendarReferenceByToken(UUID token);

    List<CalendarReference> findAllByUser_Id(Long user_id);
}
