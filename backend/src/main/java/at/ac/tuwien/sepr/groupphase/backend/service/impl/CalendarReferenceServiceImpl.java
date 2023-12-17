package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import at.ac.tuwien.sepr.groupphase.backend.repository.CalendarReferenceRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.CalendarReferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.UUID;

@Service
public class CalendarReferenceServiceImpl implements CalendarReferenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CalendarReferenceRepository calendarReferenceRepository;

    public CalendarReferenceServiceImpl(CalendarReferenceRepository calendarReferenceRepository) {
        this.calendarReferenceRepository = calendarReferenceRepository;
    }

    @Override
    public CalendarReference getFromId(long id) {
        LOGGER.debug("Get CalendarReference from id {}", id);
        return calendarReferenceRepository.getReferenceById(id);
    }

    @Override
    public CalendarReference add(CalendarReference calendarReference) {
        LOGGER.debug("Adding CalendarReference {}", calendarReference);
        var ref = calendarReferenceRepository.save(calendarReference);
        ref.setToken(generateToken());
        return ref;
    }

    @Override
    public UUID generateToken() {
        return UUID.randomUUID();
    }

    @Override
    public CalendarReference getFromToken(UUID token) {
        return calendarReferenceRepository.findCalendarReferenceByToken(token);
    }
}
