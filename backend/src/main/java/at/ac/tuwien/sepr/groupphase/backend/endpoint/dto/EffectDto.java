package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepr.groupphase.backend.entity.EffectType;

public record EffectDto(long id, String changedTitle, String changedDescription, String location, EffectType effectType) {
}
