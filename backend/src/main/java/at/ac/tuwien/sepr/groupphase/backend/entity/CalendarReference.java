package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;
import java.util.UUID;


@Entity
@Data
@Table(name = "Calendars")
public class CalendarReference {
    @ManyToMany(mappedBy = "calendarReferences", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Configuration> configurations;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private UUID token;
    @Column()
    private String name;
    @Column()
    private String link;
    @Lob
    @Column(name = "ical_data", columnDefinition = "BLOB")
    private byte[] icalData;
    @Column()
    private Long enabledDefaultConfigurations;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private ApplicationUser user;
}
