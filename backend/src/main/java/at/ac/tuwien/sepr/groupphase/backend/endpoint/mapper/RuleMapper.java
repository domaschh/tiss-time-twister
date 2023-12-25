package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.RuleDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Rule;
import org.mapstruct.Mapper;

@Mapper(uses = ConfigurationMapper.class)
public interface RuleMapper {
    Rule dtoToEvent(RuleDto ruleDto);

    RuleDto eventToDto(Rule rule);
}
