package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

public interface PipelineService {
    Calendar pipeCalendar(UUID token) throws ParserException, IOException, URISyntaxException;

    Calendar previewConfiguration(long id, List<Configuration> configurations, String username) throws NotFoundException, ParserException, IOException, URISyntaxException;
}
