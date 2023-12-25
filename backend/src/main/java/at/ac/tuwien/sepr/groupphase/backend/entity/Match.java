package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import net.fortuna.ical4j.model.component.VEvent;

@Entity
@Data
@Table(name = "Matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String summary;
    @Column
    private String description;
    @Column
    private String location;
    @Column
    private String uid;

    public boolean matches(VEvent tevent) {
        if (tevent == null) {
            return false;
        }

        boolean summaryMatches = (summary == null) || (tevent.getSummary().isPresent() && tevent.getSummary()
                                                                                                .get()
                                                                                                .getValue()
                                                                                                .contains(summary));
        boolean descriptionMatches = (description == null) || (tevent.getDescription().isPresent() && tevent.getDescription()
                                                                                                            .get()
                                                                                                            .getValue()
                                                                                                            .contains(description));
        boolean locationMatches = (location == null) || (tevent.getLocation().isPresent() && tevent.getLocation()
                                                                                                   .get()
                                                                                                   .getValue()
                                                                                                   .contains(location));
        boolean uidMatches = (uid == null) || (tevent.getUid().isPresent() && tevent.getUid().get().getValue().contains(uid));

        return summaryMatches && descriptionMatches && locationMatches && uidMatches;
    }
}
