package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.ConfigurationDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = CalendarReferenceMapper.class)
public interface ConfigurationMapper {
    Configuration toEntity(ConfigurationDto configurationDto);

    ConfigurationDto toDto(Configuration config);

    List<ConfigurationDto> toDtoList(List<Configuration> config);

    List<Configuration> toEntityList(List<ConfigurationDto> config);
}
