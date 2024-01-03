package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.service.CalendarReferenceService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EventMapperImpl implements EventMapper {

    private CalendarReferenceService calendarRefService;

    public EventMapperImpl(CalendarReferenceService calendarRefService) {
        this.calendarRefService = calendarRefService;
    }

    @Override
    public Event dtoToEvent(EventDto eventDto) {
        if (eventDto == null) {
            return null;
        }

        Event event = new Event();

        event.setId(eventDto.id());
        event.setTitle(eventDto.title());
        event.setStartTime(eventDto.startTime());
        event.setEndTime(eventDto.endTime());
        event.setLocation(eventDto.location());

        CalendarReference calendar = calendarRefService.getFromId(eventDto.calendarId());
        event.setCalendar(calendar);

        return event;
    }

    @Override
    public EventDto eventToDto(Event event) {
        if (event == null) {
            return null;
        }

        Long id = null;
        String title = null;
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        String location = null;
        Long calendarId = null;

        id = event.getId();
        title = event.getTitle();
        startTime = event.getStartTime();
        endTime = event.getEndTime();
        location = event.getLocation();
        calendarId = event.getCalendarId();

        EventDto eventDto = new EventDto(id, title, startTime, endTime, location, calendarId);

        return eventDto;
    }
}
