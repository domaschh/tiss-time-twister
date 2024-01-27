package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.entity.*;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.*;
import at.ac.tuwien.sepr.groupphase.backend.service.ConfigurationService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ConfigurationRepository configurationRepository;
    private final CalendarReferenceRepository calendarReferenceRepository;
    private final ApplicationUserRepository applicationUserRepository;
    private final PublicConfigurationRepository publicConfigurationRepository;
    private final RuleRepository ruleRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public ConfigurationServiceImpl(ConfigurationRepository configurationRepository,
                                    CalendarReferenceRepository calendarReferenceRepository,
                                    ApplicationUserRepository applicationUserRepository,
                                    PublicConfigurationRepository publicConfigurationRepository,
                                    RuleRepository ruleRepository) {
        this.configurationRepository = configurationRepository;
        this.calendarReferenceRepository = calendarReferenceRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.publicConfigurationRepository = publicConfigurationRepository;
        this.ruleRepository = ruleRepository;
    }

    @Override
    @Transactional
    public Configuration update(Configuration configuration, String username, Long calendarReferenceId) {
        LOGGER.debug("Update Configuration {}, user:{}", configuration.getTitle(), username);

        CalendarReference calendarReference = calendarReferenceRepository.findById(calendarReferenceId)
                                                                         .orElseThrow(() -> new EntityNotFoundException(
                                                                             "CalendarReference not found"));
        if (!calendarReference.getUser().getEmail().equals(username)) {
            throw new AccessDeniedException("Can Not Assign Configuration to Calendar that is not owned.");
        }

        configuration.setCalendarReference(calendarReference);
        if (configuration.getUser() == null) {
            configuration.setUser(applicationUserRepository.getApplicationUserByEmail(username));
        } else if (configuration.getId() != null
                   && !configurationRepository.findById(configuration.getId())
                                              .orElseThrow(NotFoundException::new)
                                              .getUser()
                                              .getEmail()
                                              .equals(username)) {
            throw new AccessDeniedException("Can Not Create/Update Configurations that are not owned");
        }

        if (configuration.getId() != null) {
            Configuration fetchDb = configurationRepository.findById(configuration.getId()).orElseThrow(() -> new EntityNotFoundException(
                "Config not found"));

            fetchDb.setTitle(configuration.getTitle());
            fetchDb.setDescription(configuration.getDescription());
            fetchDb.setPublished(configuration.isPublished());
            fetchDb.setRules(configuration.getRules());
            fetchDb.setClonedFromId(configuration.getClonedFromId());
            fetchDb.setCalendarReference(calendarReference);

            if (!fetchDb.isPublished()) {
                if (fetchDb.getClonedFromId() == null) {
                    this.deletePublicByOriginalId(fetchDb.getId());
                }
            }
            return configurationRepository.save(fetchDb);
        }
        configuration.getRules().forEach(r -> {
            r.setConfiguration(configuration);
            ruleRepository.save(r);
        });

        return configurationRepository.save(configuration);
    }

    private void deletePublicByOriginalId(Long id) {
        this.publicConfigurationRepository.deletePublicConfigurationByInitialConfigurationId(id);
    }


    @Override
    @Transactional
    public void delete(Long id, String username) {
        LOGGER.debug("Delete Configuration by id {}", id);

        if (!configurationRepository.findById(id).orElseThrow(NotFoundException::new).getUser().getEmail().equals(username)) {
            throw new AccessDeniedException("Can Not Delete Configurations that are not owned");
        }

        configurationRepository.deleteById(id);
    }

    @Override
    public Configuration getById(Long id) {
        LOGGER.debug("Get Configuration by id {}", id);
        try {
            return configurationRepository.getReferenceById(id);
        } catch (JpaObjectRetrievalFailureException e) {
            throw new NotFoundException();
        }
    }

    @Override
    public List<Configuration> getAllByUser(String username) {
        LOGGER.debug("Get all Configurations for user {}", username);
        var user = applicationUserRepository.getApplicationUserByEmail(username);
        if (user != null) {
            var calendarReferences = calendarReferenceRepository.findAllByUser(user);
            return calendarReferences.stream().flatMap(cr -> cr.getConfigurations().stream()).toList();
        } else {
            return List.of();
        }
    }

    @Override
    public List<PublicConfiguration> getAllPublicConfigurations() {
        LOGGER.debug("Get all published Configurations");
        return publicConfigurationRepository.findAll();
    }

    @Override
    public Configuration changeVisibility(Long configurationId) {
        LOGGER.debug("Change visibility for Configuration: {}", configurationId);
        var config = configurationRepository.getReferenceById(configurationId);
        config.setPublished(!config.isPublished());
        return configurationRepository.save(config);
    }

    @Override
    public boolean publish(Configuration configToPublish, String username) {
        LOGGER.debug("Publishing Configuration: {}", configToPublish);
        //        if (!configurationRepository.findById(configToPublish.getId())
        //                                    .orElseThrow(NotFoundException::new)
        //                                    .getUser()
        //                                    .getEmail()
        //                                    .equals(username)) {
        //            throw new AccessDeniedException("Can Not Publish Configurations that are not owned");
        //        }

        PublicConfiguration publicConfiguration = new PublicConfiguration();

        publicConfiguration.setTitle(configToPublish.getTitle());
        publicConfiguration.setInitialConfigurationId(configToPublish.getId());
        publicConfiguration.setDescription(configToPublish.getDescription());
        publicConfiguration.setPublished(configToPublish.isPublished());
        publicConfiguration.setRules(configToPublish.getRules().stream().map(rule -> {
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
            e.setTag(rule.getEffect().getTag());

            r.setConfiguration(publicConfiguration);
            r.setMatch(m);
            r.setEffect(e);
            return r;
        }).toList());

        publicConfiguration.setOwningUser(username);

        var createdPublic = this.publicConfigurationRepository.save(publicConfiguration);
        createdPublic.getRules().forEach(r -> {
            r.setConfiguration(createdPublic);
            ruleRepository.save(r);
        });
        return true;
    }

    @Override
    @Transactional
    public void deletePublic(Long id, String username) {
        LOGGER.debug("Removing published Configuration withId:{}", id);
        if (!publicConfigurationRepository
            .findById(id)
            .orElseThrow(NotFoundException::new)
            .getOwningUser()
            .equals(username)) {
            throw new AccessDeniedException("Can Not remove published Configurations that are not owned");
        }
        PublicConfiguration publicConf = publicConfigurationRepository.getReferenceById(id);
        LOGGER.debug(publicConf.toString());
        var clonedFrom = configurationRepository.findById(publicConf.getInitialConfigurationId());
        if (clonedFrom.isPresent()) {
            clonedFrom.get().setPublished(false);
            configurationRepository.save(clonedFrom.get());
        }

        this.publicConfigurationRepository.delete(publicConf);
    }
}
