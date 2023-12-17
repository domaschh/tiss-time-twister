package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.UUID;

public class CalendarReferenceDto {
    private Long id;
    private UUID token;
    @NotNull
    private String name;
    @URL
    private String link;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public static final class CalendarReferenceBuilder {
        private Long id;
        private String name;
        private String link;

        public CalendarReferenceBuilder() {
            //
        }

        public CalendarReferenceBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CalendarReferenceBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CalendarReferenceBuilder link(String link) {
            this.link = link;
            return this;
        }

        public CalendarReferenceDto build() {
            CalendarReferenceDto calendarReferenceDto = new CalendarReferenceDto();
            calendarReferenceDto.setId(id);
            calendarReferenceDto.setName(name);
            calendarReferenceDto.setLink(link);
            return calendarReferenceDto;
        }
    }

}
