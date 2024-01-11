package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.Tag;

import java.util.List;

public interface TagService {
    Tag add(Tag tag);

    List<Tag> getTagsForUser(String email);

    void delete(Long id);

    Tag getTagById(Long id);
}
