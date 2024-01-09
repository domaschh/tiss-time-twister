package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationDto {
    private Long id;
    private Long clonedFromId;
    private Long calendarReferenceId;
    private String title;
    private String description;
    private List<RuleDto> rules;
    private boolean published;
}
