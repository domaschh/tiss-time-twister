package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private MatchType summaryMatchType;
    @Column
    private String description;
    @Column
    private MatchType descriptionMatchType;
    @Column
    private String location;
    @Column
    private MatchType locationMatchType;
    @Column
    private String uid;

    public void setSummary(MatchType matchType, String summary) {
        this.summaryMatchType = matchType;
        this.summary = summary;
    }

    public void setDescription(MatchType matchType, String description) {
        this.descriptionMatchType = matchType;
        this.description = description;
    }

    public void setLocation(MatchType matchType, String location) {
        this.locationMatchType = matchType;
        this.location = location;
    }


    public boolean matches(VEvent tevent) {
        if (tevent == null) {
            return false;
        }


        boolean summaryMatches = (summary == null) || (tevent.getSummary().isPresent()
                                                       && evaluate(tevent.getSummary()
                                                                         .get()
                                                                         .getValue(),
                                                                   summaryMatchType,
                                                                   summary));
        boolean descriptionMatches = (description == null) || (tevent.getDescription().isPresent()
                                                               && evaluate(tevent.getDescription()
                                                                                 .get()
                                                                                 .getValue(),
                                                                           descriptionMatchType,
                                                                           description));
        boolean locationMatches = (location == null) || (tevent.getLocation().isPresent()
                                                         && evaluate(tevent.getLocation()
                                                                           .get()
                                                                           .getValue(),
                                                                     locationMatchType,
                                                                     location));
        boolean uidMatches = (uid == null) || (tevent.getUid().isPresent() && tevent.getUid().get().getValue().contains(uid));

        return summaryMatches && descriptionMatches && locationMatches && uidMatches;
    }

    private boolean evaluate(String actual, MatchType matchType, String expected) {
        return switch (matchType) {
            case CONTAINS -> actual.contains(expected);
            case STARTS_WITH -> actual.startsWith(expected);
            case EQUALS -> actual.equals(expected);
            case REGEX -> actual.matches(expected);
        };

    }
}
