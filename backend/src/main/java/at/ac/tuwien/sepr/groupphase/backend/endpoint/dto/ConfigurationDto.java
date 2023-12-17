package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import java.util.List;

public class ConfigurationDto {
    private Long Id;
    private String title;
    private String description;
    private List<CalendarReferenceDto> calendarRefences;
    private List<RuleDto> rules;

}
