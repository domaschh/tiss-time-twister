package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.TagDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public interface TagMapper {
    Tag dtoToTag(TagDto tagDto);

    TagDto tagToDto(Tag tag);
}
