package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Rules")
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private String rule;

    @ManyToOne()
    @JoinColumn(name = "config_id")
    private Configuration configuration;

    @OneToOne
    private Effect effect;

    @OneToOne
    private Match match;
}
