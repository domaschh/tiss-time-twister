package at.ac.tuwien.sepr.groupphase.backend.entity;

import net.fortuna.ical4j.model.component.VTimeZone;

import java.util.List;

public class Tcalendar {
    private VTimeZone timezone;
    private List<Tevent> eventList;

    public Tcalendar(VTimeZone timezone, List<Tevent> eventList) {
        this.timezone = timezone;
        this.eventList = eventList;
    }

    public VTimeZone getTimezone() {
        return timezone;
    }

    public void setTimezone(VTimeZone timezone) {
        this.timezone = timezone;
    }

    public List<Tevent> getEventList() {
        return eventList;
    }

    public void setEventList(List<Tevent> eventList) {
        this.eventList = eventList;
    }
}
