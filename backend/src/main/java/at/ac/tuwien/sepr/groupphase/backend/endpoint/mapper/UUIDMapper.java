package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper
public interface UUIDMapper {
    UUID map(UUID value);
}
