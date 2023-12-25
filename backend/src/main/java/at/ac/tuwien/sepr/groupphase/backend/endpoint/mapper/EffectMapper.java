package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EffectDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Effect;

public interface EffectMapper {
    Effect dtoToEvent(EffectDto effectDto);

    EffectDto eventToDto(Effect effect);
}
