package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
import at.ac.tuwien.sepr.groupphase.backend.entity.Rule;
import at.ac.tuwien.sepr.groupphase.backend.repository.RuleRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.RuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final RuleRepository ruleRepository;

    @Autowired
    public RuleServiceImpl(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Override
    public Rule add(Rule rule) {
        LOGGER.debug("Adding Rule {}", rule);
        return ruleRepository.save(rule);
    }

    @Override
    public Rule getById(Long id) {
        LOGGER.debug("Getting Rule with id {}", id);
        return ruleRepository.getReferenceById(id);
    }

    @Override
    public List<Rule> getAllByConfiguration(Configuration config) {
        LOGGER.debug("Getting all Rule from Configuration {}", config);
        return config.getRules();
    }
}
