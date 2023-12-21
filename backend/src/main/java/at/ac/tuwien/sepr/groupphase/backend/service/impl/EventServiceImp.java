package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImp implements EventService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final EventRepository eventRepository;

    public EventServiceImp(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event getEventById(long id) throws NotFoundException {
        LOGGER.debug("Get Event by id {}", id);
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            return event.get();
        } else {
            throw new NotFoundException("Event with id" + id + " not found!");
        }
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

    @Override
    public Event updateEvent(Long id, Event event) throws NotFoundException {
        LOGGER.debug("Update Event {} with data {}", id, event);
        Optional<Event> toUpdate = eventRepository.findById(id);
        if (toUpdate.isPresent()) {
            Event updatedEvent = toUpdate.get();
            updatedEvent.setTitle(event.getTitle());
            updatedEvent.setStartTime(event.getStartTime());
            updatedEvent.setEndTime(event.getEndTime());
            updatedEvent.setLocation(event.getLocation());
            updatedEvent.setCalendar(event.getCalendar());
            return eventRepository.save(updatedEvent);
        } else {
            throw new NotFoundException("Event to update with id " + id + " not found!");
        }

    }

    @Override
    public void delete(Long id) {
        LOGGER.debug("Delete event with id {}", id);
        eventRepository.deleteById(id);
    }
}
