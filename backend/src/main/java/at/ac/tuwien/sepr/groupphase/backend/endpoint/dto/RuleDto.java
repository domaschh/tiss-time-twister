package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;

public class RuleDto {
    private Long id;
    private String value;
    private Configuration configuration;
    private EffectDto effect;
    private MatchDto match;
}
