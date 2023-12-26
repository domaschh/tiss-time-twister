package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepr.groupphase.backend.entity.MatchType;

public record MatchDto(Long id,
                       MatchType summaryMatchType,
                       String summary,
                       MatchType descriptionMatchType,
                       String description,
                       MatchType locationMatchType,
                       String location
) {
}
