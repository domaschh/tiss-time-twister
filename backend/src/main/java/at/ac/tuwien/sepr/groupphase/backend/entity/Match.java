package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.fortuna.ical4j.model.component.VEvent;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Match {
    @Id
    @GeneratedValue
    private long Id;

    private String summary;
    private String description;
    private String location;
    private String uid;


    public Match() {}

    public boolean matches(VEvent tevent) {
        if (tevent == null) {
            return false;
        }

        boolean summaryMatches = (summary == null) || (tevent.getSummary().isPresent() && tevent.getSummary().get().getValue().equals(summary));
        boolean descriptionMatches = (description == null) || (tevent.getDescription().isPresent() && tevent.getDescription().get().getValue().equals(description));
        boolean locationMatches = (location == null) || (tevent.getLocation().isPresent() && tevent.getLocation().get().getValue().equals(location));
        boolean uidMatches = (uid == null) || (tevent.getUid().isPresent() && tevent.getUid().get().getValue().equals(uid));

        return summaryMatches && descriptionMatches && locationMatches && uidMatches;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
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
