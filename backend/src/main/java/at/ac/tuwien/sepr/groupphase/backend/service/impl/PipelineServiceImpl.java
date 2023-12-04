package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.repository.ConfigurationRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.PipelineService;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.stereotype.Service;

@Service
public class PipelineServiceImpl implements PipelineService {
    private ConfigurationRepository configurationRepository;

    public PipelineServiceImpl(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Override
    public Calendar pipeCalendar(String calendarUrl) {
        return null;
    }
}
