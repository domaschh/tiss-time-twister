package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

public record RuleDto(long id, String value,EffectDto effect, MatchDto match) {
}
