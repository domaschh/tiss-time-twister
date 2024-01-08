package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "publicConfigurations")
@Getter
@Setter
public class PublicConfiguration extends Configuration {

    @Column
    private String owningUser;

    @Column
    private Long initialConfigurationId;
}
