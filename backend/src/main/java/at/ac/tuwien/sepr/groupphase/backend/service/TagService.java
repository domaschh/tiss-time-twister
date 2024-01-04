package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.Tag;

import java.util.List;

public interface TagService {
    public Tag add(Tag tag);

    public List<Tag> getTagsForUser(String email);

    void delete(Long id);
}
