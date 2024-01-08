package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.ConfigurationDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(uses = CalendarReferenceMapper.class)
public interface ConfigurationMapper {
    Configuration toEntity(ConfigurationDto configurationDto);

    @Mapping(source = "calendarReference", target = "calendarReferenceId", qualifiedByName = "calendarReferenceToId")
    ConfigurationDto toDto(Configuration config);

    List<ConfigurationDto> toDtoList(List<Configuration> config);

    List<Configuration> toEntityList(List<ConfigurationDto> config);

    @Named("calendarReferenceToId")
    default Long calendarReferenceToId(CalendarReference calendarReference) {
        return calendarReference == null ? null : calendarReference.getId();
    }
}
