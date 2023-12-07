package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EventMapper {
    Event dtoToEvent(EventDto calendarReferenceDto);

    EventDto eventToDto(Event calendarReferenceDto);
}
