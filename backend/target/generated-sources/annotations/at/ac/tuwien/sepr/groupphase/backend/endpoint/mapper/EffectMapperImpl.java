package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EffectDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Effect;
import at.ac.tuwien.sepr.groupphase.backend.entity.EffectType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-28T12:43:56+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class EffectMapperImpl implements EffectMapper {

    @Override
    public Effect dtoToEvent(EffectDto effectDto) {
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
        effect.setTag( effectDto.tag() );
        effect.setEffectType( effectDto.effectType() );

        return effect;
    }

    @Override
    public EffectDto eventToDto(Effect effect) {
        if ( effect == null ) {
            return null;
        }

        Long id = null;
        String changedTitle = null;
        String changedDescription = null;
        String location = null;
        String tag = null;
        EffectType effectType = null;

        id = effect.getId();
        changedTitle = effect.getChangedTitle();
        changedDescription = effect.getChangedDescription();
        location = effect.getLocation();
        tag = effect.getTag();
        effectType = effect.getEffectType();

        EffectDto effectDto = new EffectDto( id, changedTitle, changedDescription, location, tag, effectType );

        return effectDto;
    }
}
