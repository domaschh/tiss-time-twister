package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.ConfigurationDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
import org.mapstruct.Mapper;

@Mapper(uses = CalendarReferenceMapper.class)
public interface ConfigurationMapper {
    Configuration dtoToEvent(ConfigurationDto configurationDto);

    ConfigurationDto eventToDto(Configuration effect);
}
