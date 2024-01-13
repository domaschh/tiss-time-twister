package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Entity
@Data
@ToString
@Table(name = "Calendars")
public class CalendarReference {
    @OneToMany(mappedBy = "calendarReference", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Configuration> configurations;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private UUID token;
    @Column()
    private String name;
    @Column()
    private String color;
    @Column()
    private String link;

    @Column(name = "ical_data")
    @Lob
    private byte[] icalData;
    @Column()
    private Long enabledDefaultConfigurations;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private ApplicationUser user;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CalendarReference that = (CalendarReference) o;
        return new HashSet<>(configurations).containsAll(that.configurations)
               && configurations.size() == that.configurations.size()
               && Objects.equals(id, that.id)
               && Objects.equals(token, that.token)
               && Objects.equals(name, that.name)
               && Objects.equals(link, that.link)
               && Arrays.equals(icalData, that.icalData)
               && Objects.equals(enabledDefaultConfigurations, that.enabledDefaultConfigurations)
               && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(configurations, id, token, name, link, enabledDefaultConfigurations, user);
        result = 31 * result + Arrays.hashCode(icalData);
        return result;
    }
}
