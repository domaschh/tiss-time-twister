package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.entity.Tcalendar;
import at.ac.tuwien.sepr.groupphase.backend.entity.Tevent;
import at.ac.tuwien.sepr.groupphase.backend.service.TMapper;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;

import java.util.List;

public class CalendarMapper implements TMapper<Calendar, Tcalendar> {
    @Override
    public Tcalendar map(Calendar from) {
        List<Tevent> tEvents = from.getComponentList().getAll().stream().filter(VEvent.class::isInstance).map(e -> Tevent.fromVEvent((VEvent) e)).toList();
        return new Tcalendar((VTimeZone) from.getComponent("VTimeZone").get(), tEvents);
    }
}
