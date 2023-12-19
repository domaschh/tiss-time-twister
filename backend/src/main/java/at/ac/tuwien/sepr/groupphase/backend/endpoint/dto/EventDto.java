package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import java.time.LocalDate;
import java.util.Date;

public record EventDto(
    Long id,
    String title,
    String description,
    LocalDate startTime,
    LocalDate endTime,
    String location
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
    public String description() {
        return description;
    }

    @Override
    public LocalDate startTime() {
        return startTime;
    }

    @Override
    public LocalDate endTime() {
        return endTime;
    }

    @Override
    public String location() {
        return location;
    }
}
