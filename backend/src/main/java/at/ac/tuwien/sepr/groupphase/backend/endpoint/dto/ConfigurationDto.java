package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import lombok.Data;

import java.util.List;

@Data
public class ConfigurationDto {
    private Long id;
    private String title;
    private String description;
    private List<RuleDto> rules;
    private boolean published;
}
