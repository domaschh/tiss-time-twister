package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
import at.ac.tuwien.sepr.groupphase.backend.entity.Rule;

import java.util.List;

public interface RuleService {
    Rule add(Rule rule);

    Rule getById(Long id);

    List<Rule> getAllByConfiguration(Configuration config);
}
