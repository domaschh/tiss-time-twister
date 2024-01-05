package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.ConfigurationDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EffectDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.MatchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.RuleDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
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
    date = "2024-01-05T22:21:23+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class ConfigurationMapperImpl implements ConfigurationMapper {

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
        configuration.setRules( ruleDtoListToRuleList( configurationDto.getRules() ) );

        return configuration;
    }

    @Override
    public ConfigurationDto toDto(Configuration config) {
        if ( config == null ) {
            return null;
        }

        ConfigurationDto configurationDto = new ConfigurationDto();

        configurationDto.setId( config.getId() );
        configurationDto.setTitle( config.getTitle() );
        configurationDto.setDescription( config.getDescription() );
        configurationDto.setRules( ruleListToRuleDtoList( config.getRules() ) );
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

    protected Rule ruleDtoToRule(RuleDto ruleDto) {
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

    protected List<Rule> ruleDtoListToRuleList(List<RuleDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Rule> list1 = new ArrayList<Rule>( list.size() );
        for ( RuleDto ruleDto : list ) {
            list1.add( ruleDtoToRule( ruleDto ) );
        }

        return list1;
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

    protected RuleDto ruleToRuleDto(Rule rule) {
        if ( rule == null ) {
            return null;
        }

        RuleDto ruleDto = new RuleDto();

        ruleDto.setId( rule.getId() );
        ruleDto.setEffect( effectToEffectDto( rule.getEffect() ) );
        ruleDto.setMatch( matchToMatchDto( rule.getMatch() ) );

        return ruleDto;
    }

    protected List<RuleDto> ruleListToRuleDtoList(List<Rule> list) {
        if ( list == null ) {
            return null;
        }

        List<RuleDto> list1 = new ArrayList<RuleDto>( list.size() );
        for ( Rule rule : list ) {
            list1.add( ruleToRuleDto( rule ) );
        }

        return list1;
    }
}
