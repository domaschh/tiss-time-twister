package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CalendarReferenceService {

    CalendarReference getFromId(long id);

    CalendarReference add(CalendarReference calendarReference, String username);

    void deleteCalendar(Long id);

    UUID generateToken();

    Optional<CalendarReference> getFromToken(UUID token);

    List<CalendarReference> getAllForUser(String email);
}
