package at.ac.tuwien.sepr.groupphase.backend.service;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

public interface PipelineService {
    Calendar pipeCalendar(UUID token) throws ParserException, IOException, URISyntaxException;
}
