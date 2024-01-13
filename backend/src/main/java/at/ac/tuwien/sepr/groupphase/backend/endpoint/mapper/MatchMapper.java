package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.MatchDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Match;
import org.mapstruct.Mapper;

@Mapper
public interface MatchMapper {
    Match dtoToEvent(MatchDto matchDto);

    MatchDto eventToDto(Match match);
}
