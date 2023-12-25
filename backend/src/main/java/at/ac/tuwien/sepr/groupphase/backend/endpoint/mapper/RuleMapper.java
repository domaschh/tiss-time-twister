package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.RuleDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Rule;

public interface RuleMapper {
    Rule dtoToEvent(RuleDto ruleDto);

    RuleDto eventToDto(Rule rule);
}
