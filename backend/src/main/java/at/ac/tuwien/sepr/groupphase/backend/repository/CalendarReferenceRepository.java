package at.ac.tuwien.sepr.groupphase.backend.repository;

import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface CalendarReferenceRepository extends JpaRepository<CalendarReference, Long> {
    Optional<CalendarReference> findCalendarReferenceByToken(UUID token);

    List<CalendarReference> findAllByUser_Id(Long userId);

    List<CalendarReference> findAllByUser(ApplicationUser user);
}
