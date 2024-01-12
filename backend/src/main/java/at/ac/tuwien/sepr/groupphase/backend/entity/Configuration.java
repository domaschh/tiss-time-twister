package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "Configurations")
@Setter
@Getter
@ToString
public class Configuration {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "calendar_id")
    private CalendarReference calendarReference;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private String title;
    @Column()
    private String description;
    @Column()
    private boolean published;
    @Column()
    private Long clonedFromId;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private ApplicationUser user;
    @OneToMany(mappedBy = "configuration", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Rule> rules;
}
