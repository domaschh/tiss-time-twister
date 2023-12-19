package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;

import java.util.List;

public interface EventService {
    public Event getEventById(long id);

    public Event add(Event event);

    public List<Event> getEventsByCalendar(CalendarReference calendar);
}
