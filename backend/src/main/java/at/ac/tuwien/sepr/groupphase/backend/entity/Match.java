package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;

import java.util.Optional;

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

        boolean summaryMatches = evaluate(tevent.getSummary(), summaryMatchType, summary);
        boolean descriptionMatches = evaluate(tevent.getDescription(), descriptionMatchType, description);
        boolean locationMatches = evaluate(tevent.getLocation(), locationMatchType, location);
        boolean uidMatches = (uid == null) || (tevent.getUid().isPresent() && tevent.getUid().get().getValue().contains(uid));

        return summaryMatches && descriptionMatches && locationMatches && uidMatches;
    }

    private boolean evaluate(Optional<? extends Property> optional, MatchType matchType, String matchValue) {
        if (matchValue == null || matchValue.isEmpty()) {
            return true;
        }

        if (optional.isEmpty()) {
            return false;
        }
        String propertyValue = optional.get().getValue();

        return switch (matchType) {
            case CONTAINS -> propertyValue.contains(matchValue);
            case STARTS_WITH -> propertyValue.startsWith(matchValue);
            case EQUALS -> propertyValue.equals(matchValue);
            case REGEX -> propertyValue.matches(matchValue);
        };

    }
}
