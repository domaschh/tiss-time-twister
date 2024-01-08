package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Configurations")
@Setter
@Getter
public class Configuration {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "calendar_ref_config",
        joinColumns = @JoinColumn(name = "configuration_id"),
        inverseJoinColumns = @JoinColumn(name = "reference_id"))
    List<CalendarReference> calendarReferences;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private String title;
    @Column()
    private String description;
    @Column()
    private boolean published;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private ApplicationUser user;
    @OneToMany(mappedBy = "configuration", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Rule> rules;
}
