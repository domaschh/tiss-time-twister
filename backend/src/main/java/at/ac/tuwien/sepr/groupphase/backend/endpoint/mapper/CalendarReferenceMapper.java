package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.CalendarReferenceDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = ConfigurationMapper.class)
public interface CalendarReferenceMapper {

    @Mapping(target = "id", source = "id")
    CalendarReferenceDto calendarReferenceToDto(CalendarReference calendarReference);

    CalendarReference dtoToCalendarReference(CalendarReferenceDto calendarReferenceDto);
}
