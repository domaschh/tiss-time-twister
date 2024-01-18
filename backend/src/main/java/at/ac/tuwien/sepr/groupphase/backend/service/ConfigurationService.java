package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
import at.ac.tuwien.sepr.groupphase.backend.entity.PublicConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConfigurationService {
    Configuration update(Configuration configuration, String username, Long calenderReferenceId);

    void delete(Long id, String username);

    Configuration getById(Long id);

    List<Configuration> getAllByUser(String userId);

    List<PublicConfiguration> getAllPublicConfigurations();

    Configuration changeVisibility(Long configurationId);

    boolean publish(Configuration configToPublish, String username);

    void deletePublic(Long id, String username);
}
