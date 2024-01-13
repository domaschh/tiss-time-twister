package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
import at.ac.tuwien.sepr.groupphase.backend.entity.Effect;
import at.ac.tuwien.sepr.groupphase.backend.entity.Match;
import at.ac.tuwien.sepr.groupphase.backend.entity.Rule;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.CalendarReferenceRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.ConfigurationRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.CalendarReferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
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
        if (calendarReference.getId() != null
            && !calendarReferenceRepository.findById(calendarReference.getId())
                                           .orElseThrow(NotFoundException::new)
                                           .getUser()
                                           .getEmail()
                                           .equals(username)) {
            throw new AccessDeniedException("Can't modify Calendars you don't own");
        }

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

    public CalendarReference addFile(String name, MultipartFile file, String username, UUID token) throws IOException {
        LOGGER.debug("Adding File CalendarReference {}", name);
        ApplicationUser user = applicationUserRepository.getApplicationUserByEmail(username);

        CalendarReference calendarReference = token != null ? getFromToken(token).orElse(new CalendarReference()) : new CalendarReference();
        calendarReference.setName(name);
        calendarReference.setUser(user);
        if (calendarReference.getToken() == null) {
            calendarReference.setToken(generateToken());
        }
        if (calendarReference.getEnabledDefaultConfigurations() == null) {
            calendarReference.setEnabledDefaultConfigurations(0L);
        }
        calendarReference.setIcalData(file.getBytes());
        calendarReference.setLink(null);

        return calendarReferenceRepository.save(calendarReference);
    }

    @Override
    public UUID generateToken() {
        return UUID.randomUUID();
    }

    @Override
    public Optional<CalendarReference> getFromToken(UUID token) {
        LOGGER.debug("Get CalendarReference from token {}", token);
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
    public CalendarReference addConfig(Long configId, Long calendarId) {
        CalendarReference calendarReference = calendarReferenceRepository.getReferenceById(calendarId);
        if (configId < 0) { // negatives are default configs
            calendarReference.setEnabledDefaultConfigurations(calendarReference.getEnabledDefaultConfigurations() | (-configId));
        } else {
            Configuration configuration = configurationRepository
                .findById(configId)
                .orElseThrow(NotFoundException::new);
            if (calendarReference.getConfigurations() != null) {
                calendarReference.getConfigurations().add(configuration);
            } else {
                calendarReference.setConfigurations(List.of(configuration));
            }

            if (configuration != null) {
                configuration.setCalendarReference(calendarReference);
            }
        }
        return calendarReferenceRepository.save(calendarReference);
    }

    @Override
    public CalendarReference clonePublicConfig(Long configId, Long calendarId) {
        Configuration configToAdd = configurationRepository.getReferenceById(configId);
        CalendarReference calendarReference = calendarReferenceRepository.getReferenceById(calendarId);
        if (configId < 0) { // negatives are default configs
            calendarReference.setEnabledDefaultConfigurations(calendarReference.getEnabledDefaultConfigurations() | (-configId));
            return calendarReferenceRepository.save(calendarReference);
        }


        if (configToAdd.isPublished()) {
            Configuration cloned = new Configuration();

            cloned.setTitle(configToAdd.getTitle());
            cloned.setClonedFromId(configToAdd.getId());
            cloned.setCalendarReference(calendarReference);
            cloned.setDescription(configToAdd.getDescription());
            cloned.setPublished(false);

            cloned.setRules(configToAdd.getRules().stream().map(rule -> {
                var r = new Rule();

                var m = new Match();
                m.setSummary(rule.getMatch().getSummary());
                m.setSummaryMatchType(rule.getMatch().getSummaryMatchType());
                m.setDescription(rule.getMatch().getDescription());
                m.setDescriptionMatchType(rule.getMatch().getDescriptionMatchType());
                m.setLocation(rule.getMatch().getLocation());
                m.setLocationMatchType(rule.getMatch().getLocationMatchType());

                var e = new Effect();
                e.setEffectType(rule.getEffect().getEffectType());
                e.setLocation(rule.getEffect().getLocation());
                e.setChangedTitle(rule.getEffect().getChangedTitle());
                e.setChangedDescription(rule.getEffect().getChangedDescription());

                r.setConfiguration(cloned);
                r.setMatch(m);
                r.setEffect(e);
                return r;
            }).toList());

            if (calendarReference.getConfigurations() == null) {
                calendarReference.setConfigurations(List.of(cloned));
            } else {
                calendarReference.getConfigurations().add(cloned);
            }

            calendarReferenceRepository.save(calendarReference);

            return calendarReference;
        } else {
            throw new AccessDeniedException("Can't add nto public Config");
        }
    }

    @Override
    public CalendarReference removeConfig(Long configId, Long calendarId) {
        CalendarReference calendarReference = calendarReferenceRepository.getReferenceById(calendarId);
        if (configId < 0) { // negatives are default configs
            calendarReference.setEnabledDefaultConfigurations(calendarReference.getEnabledDefaultConfigurations() & ~-configId);
        } else {
            Configuration configuration = configurationRepository.getReferenceById(configId);
            if (calendarReference.getConfigurations() != null) {
                calendarReference.getConfigurations().remove(configuration);
            }
            configuration.setCalendarReference(null);
        }
        return calendarReferenceRepository.save(calendarReference);
    }

    @Override
    public void deleteCalendar(Long id) {
        LOGGER.debug("Deleting CalendarReference {}", id);
        calendarReferenceRepository.deleteById(id);
    }
}
