package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.entity.Tag;
import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.TagRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final TagRepository tagRepository;
    private final ApplicationUserRepository applicationUserRepository;


    public TagServiceImpl(TagRepository tagRepository, ApplicationUserRepository applicationUserRepository) {
        this.tagRepository = tagRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public Tag add(Tag tag) {
        LOGGER.debug("Add Tag {}", tag);
        return tagRepository.save(tag);
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
    public void delete(Long id) {
        LOGGER.debug("Delete tag with id {}", id);
        tagRepository.deleteById(id);
    }
}
