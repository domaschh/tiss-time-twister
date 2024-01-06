package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EffectDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.MatchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.RuleDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Effect;
import at.ac.tuwien.sepr.groupphase.backend.entity.EffectType;
import at.ac.tuwien.sepr.groupphase.backend.entity.Match;
import at.ac.tuwien.sepr.groupphase.backend.entity.MatchType;
import at.ac.tuwien.sepr.groupphase.backend.entity.Rule;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-06T13:53:52+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class RuleMapperImpl implements RuleMapper {

    @Override
    public Rule dtoToRule(RuleDto ruleDto) {
        if ( ruleDto == null ) {
            return null;
        }

        Rule rule = new Rule();

        if ( ruleDto.getId() != null ) {
            rule.setId( ruleDto.getId() );
        }
        rule.setEffect( effectDtoToEffect( ruleDto.getEffect() ) );
        rule.setMatch( matchDtoToMatch( ruleDto.getMatch() ) );

        return rule;
    }

    @Override
    public RuleDto ruleToDto(Rule rule) {
        if ( rule == null ) {
            return null;
        }

        RuleDto ruleDto = new RuleDto();

        ruleDto.setId( rule.getId() );
        ruleDto.setEffect( effectToEffectDto( rule.getEffect() ) );
        ruleDto.setMatch( matchToMatchDto( rule.getMatch() ) );

        return ruleDto;
    }

    @Override
    public List<Rule> toEntityList(List<RuleDto> rules) {
        if ( rules == null ) {
            return null;
        }

        List<Rule> list = new ArrayList<Rule>( rules.size() );
        for ( RuleDto ruleDto : rules ) {
            list.add( dtoToRule( ruleDto ) );
        }

        return list;
    }

    @Override
    public List<RuleDto> toDtoList(List<Rule> rules) {
        if ( rules == null ) {
            return null;
        }

        List<RuleDto> list = new ArrayList<RuleDto>( rules.size() );
        for ( Rule rule : rules ) {
            list.add( ruleToDto( rule ) );
        }

        return list;
    }

    protected Effect effectDtoToEffect(EffectDto effectDto) {
        if ( effectDto == null ) {
            return null;
        }

        Effect effect = new Effect();

        if ( effectDto.id() != null ) {
            effect.setId( effectDto.id() );
        }
        effect.setChangedTitle( effectDto.changedTitle() );
        effect.setChangedDescription( effectDto.changedDescription() );
        effect.setLocation( effectDto.location() );
        effect.setEffectType( effectDto.effectType() );

        return effect;
    }

    protected Match matchDtoToMatch(MatchDto matchDto) {
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

    protected EffectDto effectToEffectDto(Effect effect) {
        if ( effect == null ) {
            return null;
        }

        Long id = null;
        String changedTitle = null;
        String changedDescription = null;
        String location = null;
        EffectType effectType = null;

        id = effect.getId();
        changedTitle = effect.getChangedTitle();
        changedDescription = effect.getChangedDescription();
        location = effect.getLocation();
        effectType = effect.getEffectType();

        EffectDto effectDto = new EffectDto( id, changedTitle, changedDescription, location, effectType );

        return effectDto;
    }

    protected MatchDto matchToMatchDto(Match match) {
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
