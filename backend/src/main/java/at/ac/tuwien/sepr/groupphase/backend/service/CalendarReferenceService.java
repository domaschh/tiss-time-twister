package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import org.springframework.stereotype.Service;

@Service
public interface CalendarReferenceService {

    public CalendarReference getFromId(long id);

    public CalendarReference add(CalendarReference calendarReference);

}
