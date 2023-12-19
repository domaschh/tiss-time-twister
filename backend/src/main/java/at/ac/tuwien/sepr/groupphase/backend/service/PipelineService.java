package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;

import java.io.IOException;
import java.net.URISyntaxException;

public interface PipelineService {
    Calendar pipeCalendar(CalendarReference calendarReference) throws ParserException, IOException, URISyntaxException;
}
