package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Rules")
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "config_id")
    private Configuration configuration;

    @OneToOne(cascade = CascadeType.ALL)
    private Effect effect;

    @OneToOne(cascade = CascadeType.ALL)
    private Match match;
}
