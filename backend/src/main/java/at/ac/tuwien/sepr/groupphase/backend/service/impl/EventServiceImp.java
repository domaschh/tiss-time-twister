package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class EventServiceImp implements EventService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final EventRepository eventRepository;

    public EventServiceImp(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event getEventById(long id) {
        LOGGER.debug("Get Event by id {}", id);
        return eventRepository.getReferenceById(id);
    }

    @Override
    public Event add(Event event) {
        LOGGER.debug("Add Event {}", event);
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getEventsByCalendar(CalendarReference calendar) {
        LOGGER.debug("Get Event by calendar {}", calendar.getName());
        return eventRepository.findAllByCalendar(calendar);
    }
}
