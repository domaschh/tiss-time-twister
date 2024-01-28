package at.ac.tuwien.sepr.groupphase.backend.repository;

import at.ac.tuwien.sepr.groupphase.backend.entity.PublicConfiguration;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PublicConfigurationRepository extends JpaRepository<PublicConfiguration, Long> {
    void deletePublicConfigurationByInitialConfigurationId(Long id);

    PublicConfiguration findPublicConfigurationByInitialConfigurationId(Long initialConfigurationId);
}
