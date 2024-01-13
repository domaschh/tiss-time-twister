package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
import at.ac.tuwien.sepr.groupphase.backend.entity.EffectType;
import at.ac.tuwien.sepr.groupphase.backend.entity.Tag;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.ConfigurationRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.TagRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final TagRepository tagRepository;
    private final ApplicationUserRepository applicationUserRepository;
    private final ConfigurationRepository configurationRepository;

    public TagServiceImpl(TagRepository tagRepository, ApplicationUserRepository applicationUserRepository,
                          ConfigurationRepository effectRepository) {
        this.tagRepository = tagRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.configurationRepository = effectRepository;
    }

    @Override
    public Tag add(Tag tag) {
        LOGGER.debug("Add Tag {}", tag);
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTagById(Long id) {
        LOGGER.debug("Get tag {}", id);
        var tagOptional = tagRepository.findById(id);
        if (tagOptional.isEmpty()) {
            throw new NotFoundException();
        } else {
            return tagOptional.get();
        }
    }

    @Override
    public List<Tag> getTagsForUser(String email) {
        LOGGER.debug("Get all Tags for user {}", email);
        var user = applicationUserRepository.getApplicationUserByEmail(email);
        if (user != null) {
            return tagRepository.findAllByUser(user);
        } else {
            return List.of();
        }

    }

    @Override
    public List<Configuration> delete(Long id, String email) {
        var user = applicationUserRepository.getApplicationUserByEmail(email);
        if (user != null) {
            LOGGER.debug("Delete tag with id {}", id);
            var tag = tagRepository.getReferenceById(id);

            if (tag != null) {
                var configs = configurationRepository.findAllByUser(user);
                var allUserTags = configs
                    .stream()
                    .flatMap(config -> config.getRules().stream())
                    .filter(rule -> rule.getEffect().getEffectType().equals(
                        EffectType.TAG))
                    .map(rule -> rule.getEffect().getTag());

                var userEffects = allUserTags
                    .filter(tagName -> tagName.equals(tag.getTag()))
                    .toList();
                if (userEffects.isEmpty()) {
                    tagRepository.deleteById(id);
                    return List.of();
                } else {
                    return configs
                        .stream()
                        .filter(configuration -> !configuration
                            .getRules()
                            .stream()
                            .filter(rule -> {
                                if (rule.getEffect().getEffectType().equals(EffectType.TAG)) {
                                    return rule.getEffect().getTag().equals(tag.getTag());
                                }
                                return false;
                            }).toList().isEmpty()).toList();
                }
            }
        }
        throw new AccessDeniedException("Can't delete Tag");
    }
}
