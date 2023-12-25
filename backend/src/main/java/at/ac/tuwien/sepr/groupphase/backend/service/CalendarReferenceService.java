package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;

import java.util.UUID;

public interface CalendarReferenceService {

    CalendarReference getFromId(long id);

    CalendarReference add(CalendarReference calendarReference);

    void deleteCalendar(Long id);

    UUID generateToken();

    CalendarReference getFromToken(UUID token);
}
