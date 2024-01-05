package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CalendarReferenceDto {
    private Long id;
    private UUID token;

    @NotNull
    private String name;
    @URL
    private String link;
    private String icalData;
    private List<ConfigurationDto> configurations;
    private Long enabledDefaultConfigurations;

}
