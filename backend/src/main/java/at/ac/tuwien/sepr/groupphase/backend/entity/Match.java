package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import net.fortuna.ical4j.model.component.VEvent;

@Entity
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


    public Match() {
    }

    public Match(long id, String summary, String description, String location, String uid) {
        this.id = id;
        this.summary = summary;
        this.description = description;
        this.location = location;
        this.uid = uid;
    }

    public boolean matches(VEvent tevent) {
        if (tevent == null) {
            return false;
        }

        boolean summaryMatches = (summary == null) || (tevent.getSummary().isPresent() && tevent.getSummary()
                                                                                                .get()
                                                                                                .getValue()
                                                                                                .equals(summary));
        boolean descriptionMatches = (description == null) || (tevent.getDescription().isPresent() && tevent.getDescription()
                                                                                                            .get()
                                                                                                            .getValue()
                                                                                                            .equals(description));
        boolean locationMatches = (location == null) || (tevent.getLocation().isPresent() && tevent.getLocation()
                                                                                                   .get()
                                                                                                   .getValue()
                                                                                                   .equals(location));
        boolean uidMatches = (uid == null) || (tevent.getUid().isPresent() && tevent.getUid().get().getValue().equals(uid));

        return summaryMatches && descriptionMatches && locationMatches && uidMatches;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
