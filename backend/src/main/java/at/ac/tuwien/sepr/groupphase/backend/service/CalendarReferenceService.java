package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;

import java.util.UUID;

public interface CalendarReferenceService {

    public CalendarReference getFromId(long id);

    public CalendarReference add(CalendarReference calendarReference);

    public UUID generateToken();
}
