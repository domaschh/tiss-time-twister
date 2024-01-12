package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.TagDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public interface TagMapper {
    Tag dtoToTag(TagDto tagDto);

    Tag dtoToTag(TagDto tag, ApplicationUser user);

    TagDto tagToDto(Tag tag);
}
