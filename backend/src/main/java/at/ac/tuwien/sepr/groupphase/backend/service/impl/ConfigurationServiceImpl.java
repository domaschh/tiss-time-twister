package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.ConfigurationRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.ConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ConfigurationRepository configurationRepository;
    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    public ConfigurationServiceImpl(ConfigurationRepository configurationRepository,
                                    ApplicationUserRepository applicationUserRepository) {
        this.configurationRepository = configurationRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public Configuration add(Configuration configuration) {
        LOGGER.debug("Add Configuration {}", configuration);
        return configurationRepository.save(configuration);
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
        return configurationRepository.getReferenceById(id);
    }

    @Override
    public List<Configuration> getAllByUser(Long userId) {
        LOGGER.debug("Get all Configurations for user {}", userId);
        var user = applicationUserRepository.findById(userId);
        if (user.isPresent()) {
            return configurationRepository.findAllByUser(user.get());
        } else {
            return List.of();
        }
    }

    @Override
    public List<Configuration> getAllPublicConfigurations() {
        LOGGER.debug("Get all published Configurations");
        return configurationRepository.findAllByPublishedIsTrue();
    }

    @Override
    public Configuration changeVisibility(Long configurationId) {
        LOGGER.debug("Change visibility for Configuration: {}", configurationId);
        var config = configurationRepository.getReferenceById(configurationId);
        config.setPublished(!config.isPublished());
        return configurationRepository.save(config);
    }
}
