package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.entity.*;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.CalendarReferenceRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.ConfigurationRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.PublicConfigurationRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.ConfigurationService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
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

    @Autowired
    public ConfigurationServiceImpl(ConfigurationRepository configurationRepository,
                                    CalendarReferenceRepository calendarReferenceRepository,
                                    ApplicationUserRepository applicationUserRepository,
                                    PublicConfigurationRepository publicConfigurationRepository) {
        this.configurationRepository = configurationRepository;
        this.calendarReferenceRepository = calendarReferenceRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.publicConfigurationRepository = publicConfigurationRepository;
    }

    @Override
    @Transactional
    public Configuration add(Configuration configuration, String username) {
        LOGGER.debug("Get all Configurations for user {}", username);
        var user = applicationUserRepository.getApplicationUserByEmail(username);
        if (user != null) {
            configuration.setUser(user);
            if (configuration.getRules() == null) {
                configuration.setRules(List.of());
            } else {
                for (Rule r : configuration.getRules()) {
                    r.setConfiguration(configuration);
                }
            }
            return configurationRepository.save(configuration);
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public Configuration edit(Configuration configuration) {
        LOGGER.debug("Edit Configuration {}", configuration);
        return configurationRepository.save(configuration);
    }

    @Override
    public void delete(Long id) {
        LOGGER.debug("Delete Configuration by id {}", id);
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

            r.setConfiguration(publicConfiguration);
            r.setMatch(m);
            r.setEffect(e);
            return r;
        }).toList());

        publicConfiguration.setOwningUser(username);

        this.publicConfigurationRepository.save(publicConfiguration);
        return true;
    }
}
