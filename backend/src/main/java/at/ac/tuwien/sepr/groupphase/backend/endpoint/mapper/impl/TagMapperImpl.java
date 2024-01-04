package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.TagDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.TagMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.Tag;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class TagMapperImpl implements TagMapper {
    private final UserService userService;

    public TagMapperImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Tag dtoToTag(TagDto tagDto) {
        if (tagDto == null){
            return null;
        }

        Tag tag = new Tag();
        tag.setId(tagDto.id());
        tag.setTag(tagDto.tag());
        tag.setUser(userService.findApplicationUserById(tagDto.userId()));

        return tag;
    }

    @Override
    public TagDto tagToDto(Tag tag) {
        if (tag == null) {
            return null;
        }

        return new TagDto(tag.getId(), tag.getTag(), tag.getUserId());

    }
}
