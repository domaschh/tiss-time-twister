package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;

import java.util.List;

public interface EventService {
    public Event getEventById(long id) throws NotFoundException;

    public Event add(Event event);

    public List<Event> getEventsByCalendar(CalendarReference calendar);

    public Event updateEvent(Long id, Event event) throws NotFoundException;

    void delete(Long id);
}
