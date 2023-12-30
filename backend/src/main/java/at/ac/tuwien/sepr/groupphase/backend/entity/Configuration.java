package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Configurations")
@Setter
@Getter
public class Configuration {

    @ManyToMany()
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
    @OneToMany(mappedBy = "configuration", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rule> rules;
}
