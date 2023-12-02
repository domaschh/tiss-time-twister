package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.CalendarReferenceDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CalendarReferenceMapper {

    @Mapping(target = "someFieldInDto", source = "someFieldInEntity")
    CalendarReferenceDto calendarReferenceToDto(CalendarReference calendarReference);

    CalendarReference dtoToCalendarReference(CalendarReferenceDto calendarReferenceDto);
}
