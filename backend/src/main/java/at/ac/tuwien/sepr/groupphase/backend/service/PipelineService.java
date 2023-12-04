package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.Tcalendar;

public interface PipelineService {
    Tcalendar pipeCalendar(String calendarUrl);
}
