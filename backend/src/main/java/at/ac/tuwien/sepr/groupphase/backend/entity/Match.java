package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

    public boolean matches(Tevent tevent) {
        if (tevent == null) {
            return false;
        }

        boolean summaryMatches = (summary == null) || (tevent.getSUMMARY() != null && tevent.getSUMMARY().equals(summary));
        boolean descriptionMatches = (description == null) || (tevent.getDESCRIPTION() != null && tevent.getDESCRIPTION().equals(description));
        boolean locationMatches = (location == null) || (tevent.getLOCATION() != null && tevent.getLOCATION().equals(location));
        boolean uidMatches = (uid == null) || (tevent.getUID() != null && tevent.getUID().equals(uid));

        return summaryMatches && descriptionMatches && locationMatches && uidMatches;
    }
}
