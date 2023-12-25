package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;


@Entity
@Data
@Table(name = "Calendars")
public class CalendarReference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private UUID token;
    @Column()
    private String name;
    @Column()
    private String link;

    @ManyToMany(mappedBy = "calendarReferences")
    List<Configuration> configurations;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private ApplicationUser user;
}
