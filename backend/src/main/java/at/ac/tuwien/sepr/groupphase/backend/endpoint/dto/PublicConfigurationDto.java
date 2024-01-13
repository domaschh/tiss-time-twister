package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PublicConfigurationDto extends ConfigurationDto {
    private Long initialConfigurationId;
    private boolean isMine;
}
