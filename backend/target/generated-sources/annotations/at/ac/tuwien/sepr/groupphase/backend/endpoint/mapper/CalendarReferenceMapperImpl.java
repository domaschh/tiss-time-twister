package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.CalendarReferenceDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-27T19:47:47+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class CalendarReferenceMapperImpl implements CalendarReferenceMapper {

    @Autowired
    private ConfigurationMapper configurationMapper;

    @Override
    public CalendarReferenceDto calendarReferenceToDto(CalendarReference calendarReference) {
        if ( calendarReference == null ) {
            return null;
        }

        CalendarReferenceDto calendarReferenceDto = new CalendarReferenceDto();

        calendarReferenceDto.setId( calendarReference.getId() );
        calendarReferenceDto.setToken( calendarReference.getToken() );
        calendarReferenceDto.setName( calendarReference.getName() );
        calendarReferenceDto.setLink( calendarReference.getLink() );
        calendarReferenceDto.setConfigurations( configurationMapper.toDtoList( calendarReference.getConfigurations() ) );
        calendarReferenceDto.setEnabledDefaultConfigurations( calendarReference.getEnabledDefaultConfigurations() );
        calendarReferenceDto.setColor( calendarReference.getColor() );

        calendarReferenceDto.setIcalData( byteArrayToString(calendarReference.getIcalData()) );

        return calendarReferenceDto;
    }

    @Override
    public CalendarReference dtoToCalendarReference(CalendarReferenceDto calendarReferenceDto) {
        if ( calendarReferenceDto == null ) {
            return null;
        }

        CalendarReference calendarReference = new CalendarReference();

        calendarReference.setConfigurations( configurationMapper.toEntityList( calendarReferenceDto.getConfigurations() ) );
        calendarReference.setId( calendarReferenceDto.getId() );
        calendarReference.setToken( calendarReferenceDto.getToken() );
        calendarReference.setName( calendarReferenceDto.getName() );
        calendarReference.setColor( calendarReferenceDto.getColor() );
        calendarReference.setLink( calendarReferenceDto.getLink() );
        calendarReference.setEnabledDefaultConfigurations( calendarReferenceDto.getEnabledDefaultConfigurations() );

        calendarReference.setIcalData( stringToByteArray(calendarReferenceDto.getIcalData()) );

        return calendarReference;
    }
}
