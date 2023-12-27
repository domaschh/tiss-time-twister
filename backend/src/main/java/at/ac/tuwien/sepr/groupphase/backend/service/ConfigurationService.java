package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConfigurationService {
    Configuration add(Configuration configuration);

    Configuration edit(Configuration configuration);

    void delete(Long id);

    Configuration getById(Long id);

    List<Configuration> getAllByUser(String userId);

    List<Configuration> getAllPublicConfigurations();

    Configuration changeVisibility(Long configurationId);
}
