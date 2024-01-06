package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.MatchDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Match;
import at.ac.tuwien.sepr.groupphase.backend.entity.MatchType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-06T11:56:16+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class MatchMapperImpl implements MatchMapper {

    @Override
    public Match dtoToEvent(MatchDto matchDto) {
        if ( matchDto == null ) {
            return null;
        }

        Match match = new Match();

        if ( matchDto.id() != null ) {
            match.setId( matchDto.id() );
        }
        match.setSummary( matchDto.summary() );
        match.setSummaryMatchType( matchDto.summaryMatchType() );
        match.setDescription( matchDto.description() );
        match.setDescriptionMatchType( matchDto.descriptionMatchType() );
        match.setLocation( matchDto.location() );
        match.setLocationMatchType( matchDto.locationMatchType() );

        return match;
    }

    @Override
    public MatchDto eventToDto(Match match) {
        if ( match == null ) {
            return null;
        }

        Long id = null;
        MatchType summaryMatchType = null;
        String summary = null;
        MatchType descriptionMatchType = null;
        String description = null;
        MatchType locationMatchType = null;
        String location = null;

        id = match.getId();
        summaryMatchType = match.getSummaryMatchType();
        summary = match.getSummary();
        descriptionMatchType = match.getDescriptionMatchType();
        description = match.getDescription();
        locationMatchType = match.getLocationMatchType();
        location = match.getLocation();

        MatchDto matchDto = new MatchDto( id, summaryMatchType, summary, descriptionMatchType, description, locationMatchType, location );

        return matchDto;
    }
}
