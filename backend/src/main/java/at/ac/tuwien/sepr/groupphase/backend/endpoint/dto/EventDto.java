package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import java.util.Date;

public record EventDto(
    Long id,
    String title,
    String description,
    Date startTime,
    Date endTime,
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
    public Date startTime() {
        return startTime;
    }

    @Override
    public Date endTime() {
        return endTime;
    }

    @Override
    public String location() {
        return location;
    }
}
