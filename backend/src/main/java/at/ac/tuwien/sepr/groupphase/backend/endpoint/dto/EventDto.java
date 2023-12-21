package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import java.time.LocalDateTime;

public record EventDto(
    Long id,
    String title,
    LocalDateTime startTime,
    LocalDateTime endTime,
    String location,
    Long calendarId
) {

    @Override
    public Long id() {
        return id;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public LocalDateTime startTime() {
        return startTime;
    }

    @Override
    public LocalDateTime endTime() {
        return endTime;
    }

    @Override
    public String location() {
        return location;
    }
}
