package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EffectDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Effect;
import at.ac.tuwien.sepr.groupphase.backend.entity.EffectType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-06T13:53:52+0100",
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
        EffectType effectType = null;

        id = effect.getId();
        changedTitle = effect.getChangedTitle();
        changedDescription = effect.getChangedDescription();
        location = effect.getLocation();
        effectType = effect.getEffectType();

        EffectDto effectDto = new EffectDto( id, changedTitle, changedDescription, location, effectType );

        return effectDto;
    }
}
