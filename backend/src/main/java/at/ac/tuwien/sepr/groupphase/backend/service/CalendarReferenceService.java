package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import org.springframework.web.multipart.MultipartFile;

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

    CalendarReference addConfig(Long configId, Long calendarId);

    CalendarReference removeConfig(Long configId, Long calendarId);

    CalendarReference addFile(String name, MultipartFile file, String username, UUID token);
}
