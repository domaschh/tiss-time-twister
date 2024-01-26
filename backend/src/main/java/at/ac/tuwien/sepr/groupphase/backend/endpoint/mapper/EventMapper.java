package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import org.springframework.stereotype.Component;

@Component
public interface EventMapper {

    Event dtoToEvent(EventDto eventDto, String username);

    EventDto eventToDto(Event event);
}
