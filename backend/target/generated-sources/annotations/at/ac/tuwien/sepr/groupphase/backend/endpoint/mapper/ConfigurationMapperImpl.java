package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.ConfigurationDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-26T14:12:16+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class ConfigurationMapperImpl implements ConfigurationMapper {

    @Autowired
    private RuleMapper ruleMapper;

    @Override
    public Configuration toEntity(ConfigurationDto configurationDto) {
        if ( configurationDto == null ) {
            return null;
        }

        Configuration configuration = new Configuration();

        configuration.setId( configurationDto.getId() );
        configuration.setTitle( configurationDto.getTitle() );
        configuration.setDescription( configurationDto.getDescription() );
        configuration.setPublished( configurationDto.isPublished() );
        configuration.setClonedFromId( configurationDto.getClonedFromId() );
        configuration.setRules( ruleMapper.toEntityList( configurationDto.getRules() ) );

        return configuration;
    }

    @Override
    public ConfigurationDto toDto(Configuration config) {
        if ( config == null ) {
            return null;
        }

        ConfigurationDto configurationDto = new ConfigurationDto();

        configurationDto.setCalendarReferenceId( calendarReferenceToId( config.getCalendarReference() ) );
        configurationDto.setId( config.getId() );
        configurationDto.setClonedFromId( config.getClonedFromId() );
        configurationDto.setTitle( config.getTitle() );
        configurationDto.setDescription( config.getDescription() );
        configurationDto.setRules( ruleMapper.toDtoList( config.getRules() ) );
        configurationDto.setPublished( config.isPublished() );

        return configurationDto;
    }

    @Override
    public List<ConfigurationDto> toDtoList(List<Configuration> config) {
        if ( config == null ) {
            return null;
        }

        List<ConfigurationDto> list = new ArrayList<ConfigurationDto>( config.size() );
        for ( Configuration configuration : config ) {
            list.add( toDto( configuration ) );
        }

        return list;
    }

    @Override
    public List<Configuration> toEntityList(List<ConfigurationDto> config) {
        if ( config == null ) {
            return null;
        }

        List<Configuration> list = new ArrayList<Configuration>( config.size() );
        for ( ConfigurationDto configurationDto : config ) {
            list.add( toEntity( configurationDto ) );
        }

        return list;
    }
}
