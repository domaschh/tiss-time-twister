package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.CalendarReferenceRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.CalendarReferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.UUID;

@Service
public class CalendarReferenceServiceImpl implements CalendarReferenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CalendarReferenceRepository calendarReferenceRepository;
    private final ApplicationUserRepository applicationUserRepository;

    public CalendarReferenceServiceImpl(CalendarReferenceRepository calendarReferenceRepository,
                                        ApplicationUserRepository applicationUserRepository) {
        this.calendarReferenceRepository = calendarReferenceRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public CalendarReference getFromId(long id) {
        LOGGER.debug("Get CalendarReference from id {}", id);
        return calendarReferenceRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public CalendarReference add(CalendarReference calendarReference, String username) {
        LOGGER.debug("Adding CalendarReference {}", calendarReference);
        calendarReference.setToken(generateToken());
        var user = applicationUserRepository.getApplicationUserByEmail(username);
        calendarReference.setUser(user);
        return calendarReferenceRepository.save(calendarReference);
    }

    @Override
    public UUID generateToken() {
        return UUID.randomUUID();
    }

    @Override
    public CalendarReference getFromToken(UUID token) {
        return calendarReferenceRepository.findCalendarReferenceByToken(token);
    }

    @Override
    public List<CalendarReference> getAllForUser(String email) {
        LOGGER.debug("Get all Configurations for user {}", email);
        var user = applicationUserRepository.getApplicationUserByEmail(email);
        if (user != null) {
            return calendarReferenceRepository.findAllByUser_Id(user.getId());
        } else {
            return List.of();
        }
    }

    @Override
    public void deleteCalendar(Long id) {
        LOGGER.debug("Deleting CalendarReference {}", id);
        calendarReferenceRepository.deleteById(id);
    }
}
