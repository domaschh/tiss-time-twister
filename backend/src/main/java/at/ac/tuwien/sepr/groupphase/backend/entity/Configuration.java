package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Configurations")
@Setter
@Getter
public class Configuration {

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

    @ManyToMany()
    @JoinTable(
        name = "calendar_ref_config",
        joinColumns = @JoinColumn(name = "configuration_id"),
        inverseJoinColumns = @JoinColumn(name = "reference_id"))
    List<CalendarReference> calendarReferences;

    @OneToMany(mappedBy = "configuration", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Rule> rules = new ArrayList<>();
}
