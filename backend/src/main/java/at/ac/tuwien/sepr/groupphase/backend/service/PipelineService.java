package at.ac.tuwien.sepr.groupphase.backend.service;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;

import java.io.IOException;
import java.net.URISyntaxException;

public interface PipelineService {
    Calendar pipeCalendar(String calendarUrl) throws ParserException, IOException, URISyntaxException;
}
