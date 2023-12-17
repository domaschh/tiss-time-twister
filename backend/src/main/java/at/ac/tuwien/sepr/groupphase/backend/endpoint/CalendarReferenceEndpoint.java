package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.CalendarReferenceDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.CalendarReferenceMapper;
import at.ac.tuwien.sepr.groupphase.backend.service.CalendarReferenceService;
import at.ac.tuwien.sepr.groupphase.backend.service.PipelineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(value = "/api/v1/calendar")
public class CalendarReferenceEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CalendarReferenceService calendarReferenceService;
    private final CalendarReferenceMapper calendarReferenceMapper;
    private final PipelineService pipelineService;

    @Autowired
    public CalendarReferenceEndpoint(CalendarReferenceService calendarReferenceService, CalendarReferenceMapper calendarReferenceMapper,
                                     PipelineService pipelineService) {
        this.calendarReferenceService = calendarReferenceService;
        this.calendarReferenceMapper = calendarReferenceMapper;
        this.pipelineService = pipelineService;
    }

    @Secured("ROLE_USER")
    @PutMapping
    @Operation(summary = "Import a CalendarReference", security = @SecurityRequirement(name = "apiKey"))
    public CalendarReferenceDto importCalendarReference(@Valid @RequestBody CalendarReferenceDto calendarReferenceDto) {
        LOGGER.info("Put /api/v1/calendar/ body:{}", calendarReferenceDto);
        return calendarReferenceMapper.calendarReferenceToDto(
            calendarReferenceService.add(
                calendarReferenceMapper.dtoToCalendarReference(calendarReferenceDto)));
    }

    @Secured("ROLE_USER")
    @GetMapping("/{id}")
    @Operation(summary = "Get a stored CalendarReference", security = @SecurityRequirement(name = "apiKey"))
    public CalendarReferenceDto getCalendarReference(@PathVariable Long id) {
        LOGGER.info("Get /api/v1/calendar/{}", id);
        return calendarReferenceMapper.calendarReferenceToDto(calendarReferenceService.getFromId(id));
    }


    /**
     * <p> Exports the Calendar associated with the given token.</p>
     * <p> Tokens are specific to a user or a tagged subset of their managed calendar. </p>
     * <br>
     * <p> unsecured to provide calendar synchronisation</p>
     *
     * @param token the user/tag specific token
     * @return ics file containing the adjusted calendar
     */
    @PermitAll
    @GetMapping("/export/{token}")
    @Operation(summary = "Export a calender from its url")
    public ResponseEntity<Resource> exportCalendarFile(@PathVariable String token) {
        LOGGER.info("Get /api/v1/calendar/get/export/{location}");

        String icsContent = """
            BEGIN:VCALENDAR
            VERSION:2.0
            PRODID:-//Your Organization//Your Application//EN
            CALSCALE:GREGORIAN
            BEGIN:VEVENT
            SUMMARY:Dummy Event
            DESCRIPTION:This is a dummy event for a dummy calendar.
            CATEGORIES:HOLIDAY
            DTSTART:20000101T000000Z
            DTEND:20500101T000000Z
            UID:unique-event-id-123456789
            SEQUENCE:0
            STATUS:CONFIRMED
            TRANSP:OPAQUE
            END:VEVENT
            END:VCALENDAR
            """;

        byte[] fileContent = icsContent.getBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + token + ".ics");

        return ResponseEntity.ok()
                             .headers(headers)
                             .contentLength(fileContent.length)
                             .contentType(MediaType.parseMediaType("text/calendar"))
                             .body(new ByteArrayResource(fileContent));
    }

}
