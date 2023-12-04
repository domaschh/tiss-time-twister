package at.ac.tuwien.sepr.groupphase.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.fortuna.ical4j.model.component.VEvent;

@AllArgsConstructor
@Getter
@Setter
public class Tevent {
    private String DTSART;
    private String DTEND;
    private String SUMMARY;
    private String LOCATION;
    private String CATEGORIES;
    private String DESCRIPTION;
    private String UID;
    private String DTSTAMP;

    public static Tevent fromVEvent(VEvent event) {
        return new Tevent(event.getDateTimeStart().toString(),
                          event.getDateTimeEnd().toString(),
                          event.getSummary().toString(),
                          event.getLocation().toString(),
                          event.getCategories().toString(),
                          event.getDescription().toString(),
                          event.getUid().toString(),
                          event.getDateTimeStamp().toString());
    }
}
