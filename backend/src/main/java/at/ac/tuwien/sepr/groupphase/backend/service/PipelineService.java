package at.ac.tuwien.sepr.groupphase.backend.service;

import net.fortuna.ical4j.model.Calendar;

public interface PipelineService {
    Calendar pipeCalendar(String calendarUrl);
}
