package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

public record TagDto(
    Long id,
    String tag,
    Long userId
) {}
