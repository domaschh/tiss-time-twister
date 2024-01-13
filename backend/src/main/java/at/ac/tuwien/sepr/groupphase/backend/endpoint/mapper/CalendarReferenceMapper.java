package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.CalendarReferenceDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.nio.charset.StandardCharsets;

@Mapper(uses = ConfigurationMapper.class)
public interface CalendarReferenceMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "icalData", expression = "java(byteArrayToString(calendarReference.getIcalData()))")
    CalendarReferenceDto calendarReferenceToDto(CalendarReference calendarReference);

    @Mapping(target = "icalData", expression = "java(stringToByteArray(calendarReferenceDto.getIcalData()))")
    CalendarReference dtoToCalendarReference(CalendarReferenceDto calendarReferenceDto);

    default String byteArrayToString(byte[] bytes) {
        return bytes != null ? new String(bytes, StandardCharsets.UTF_8) : null;
    }

    default byte[] stringToByteArray(String string) {
        return string != null ? string.getBytes(StandardCharsets.UTF_8) : null;
    }
}
