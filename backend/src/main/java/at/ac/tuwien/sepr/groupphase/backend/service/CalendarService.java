package at.ac.tuwien.sepr.groupphase.backend.service;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;

import java.io.IOException;

public interface CalendarService {
    Calendar fetchCalendarByUrl(String url) throws ParserException, IOException;
}
