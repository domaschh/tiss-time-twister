package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.RuleDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
import at.ac.tuwien.sepr.groupphase.backend.entity.Rule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(uses = ConfigurationMapper.class)
public interface RuleMapper {
    Rule dtoToRule(RuleDto ruleDto);

    @Mapping(source = "configuration", target = "configId", qualifiedByName = "configToId")
    RuleDto ruleToDto(Rule rule);

    List<Rule> toEntityList(List<RuleDto> rules);

    List<RuleDto> toDtoList(List<Rule> rules);

    @Named("configToId")
    default Long configToId(Configuration config) {
        return config == null ? null : config.getId();
    }
}
