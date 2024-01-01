package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.CalendarReferenceRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.ConfigurationRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.CalendarReferenceService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CalendarReferenceServiceImpl implements CalendarReferenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CalendarReferenceRepository calendarReferenceRepository;
    private final ConfigurationRepository configurationRepository;
    private final ApplicationUserRepository applicationUserRepository;

    public CalendarReferenceServiceImpl(CalendarReferenceRepository calendarReferenceRepository,
                                        ConfigurationRepository configurationRepository,
                                        ApplicationUserRepository applicationUserRepository) {
        this.calendarReferenceRepository = calendarReferenceRepository;
        this.configurationRepository = configurationRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public CalendarReference getFromId(long id) {
        LOGGER.debug("Get CalendarReference from id {}", id);
        return calendarReferenceRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public CalendarReference add(CalendarReference calendarReference, String username) {
        if (calendarReference.getEnabledDefaultConfigurations() == null) {
            calendarReference.setEnabledDefaultConfigurations(0L);
        }
        LOGGER.debug("Adding CalendarReference {}", calendarReference);
        calendarReference.setToken(generateToken());
        calendarReference.setEnabledDefaultConfigurations(0L);
        var user = applicationUserRepository.getApplicationUserByEmail(username);
        calendarReference.setUser(user);
        return calendarReferenceRepository.save(calendarReference);
    }

    @Override
    public UUID generateToken() {
        return UUID.randomUUID();
    }

    @Override
    public Optional<CalendarReference> getFromToken(UUID token) {
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
    @Transactional
    public CalendarReference addConfig(Long configId, Long calendarId) {
        CalendarReference calendarReference = calendarReferenceRepository.getReferenceById(calendarId);
        if (configId < 0) { // negatives are default configs
            calendarReference.setEnabledDefaultConfigurations(calendarReference.getEnabledDefaultConfigurations() | (-configId));
        } else {

            Configuration configuration = configurationRepository.findById(configId).orElseThrow(NotFoundException::new);
            if (!calendarReference.getConfigurations().contains(configuration)) {
                calendarReference.getConfigurations().add(configuration);
                if (configuration.getCalendarReferences() == null) {
                    configuration.setCalendarReferences(List.of(calendarReference));
                } else {
                    configuration.getCalendarReferences().add(calendarReference);
                }
            }
        }
        return calendarReferenceRepository.save(calendarReference);
    }

    @Override
    @Transactional
    public CalendarReference removeConfig(Long configId, Long calendarId) {
        CalendarReference calendarReference = calendarReferenceRepository.getReferenceById(calendarId);
        if (configId < 0) { // negatives are default configs
            calendarReference.setEnabledDefaultConfigurations(calendarReference.getEnabledDefaultConfigurations() & ~-configId);
        } else {
            Configuration configuration = configurationRepository.getReferenceById(configId);
            calendarReference.getConfigurations().remove(configuration);
            configuration.getCalendarReferences().remove(calendarReference);
        }
        return calendarReferenceRepository.save(calendarReference);
    }

    @Override
    public void deleteCalendar(Long id) {
        LOGGER.debug("Deleting CalendarReference {}", id);
        calendarReferenceRepository.deleteById(id);
    }
}
