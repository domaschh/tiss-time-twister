package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.PublicConfigurationDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import at.ac.tuwien.sepr.groupphase.backend.entity.PublicConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface PublicConfigMapper {
    PublicConfiguration toEntity(PublicConfigurationDto configurationDto);

    @Mapping(source = "calendarReference", target = "calendarReferenceId", qualifiedByName = "calendarReferenceToId")
    PublicConfigurationDto toDto(PublicConfiguration config);

    @Named("calendarReferenceToId")
    default Long calendarReferenceToId(CalendarReference calendarReference) {
        return calendarReference == null ? null : calendarReference.getId();
    }
}
