package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-07T23:03:46+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class EventMapperImpl implements EventMapper {

    @Override
    public Event dtoToEvent(EventDto calendarReferenceDto) {
        if ( calendarReferenceDto == null ) {
            return null;
        }

        Event event = new Event();

        event.setId( calendarReferenceDto.id() );
        event.setTitle( calendarReferenceDto.title() );
        event.setStartTime( calendarReferenceDto.startTime() );
        event.setEndTime( calendarReferenceDto.endTime() );
        event.setLocation( calendarReferenceDto.location() );

        return event;
    }

    @Override
    public EventDto eventToDto(Event calendarReferenceDto) {
        if ( calendarReferenceDto == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        String location = null;

        id = calendarReferenceDto.getId();
        title = calendarReferenceDto.getTitle();
        startTime = calendarReferenceDto.getStartTime();
        endTime = calendarReferenceDto.getEndTime();
        location = calendarReferenceDto.getLocation();

        Long calendarId = null;

        EventDto eventDto = new EventDto( id, title, startTime, endTime, location, calendarId );

        return eventDto;
    }
}
