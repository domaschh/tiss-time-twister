package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.CalendarReferenceDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.CalendarReferenceMapper;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.service.CalendarReferenceService;
import at.ac.tuwien.sepr.groupphase.backend.service.PipelineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URISyntaxException;
import java.util.UUID;

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
    public CalendarReferenceDto importCalendarReference(@RequestBody CalendarReferenceDto calendarReferenceDto) {
        LOGGER.info("Put /api/v1/calendar/body:{}", calendarReferenceDto);
        return calendarReferenceMapper.calendarReferenceToDto(
            calendarReferenceService.add(
                calendarReferenceMapper.dtoToCalendarReference(calendarReferenceDto)));
    }

    @Secured("ROLE_USER")
    @GetMapping("/{id}")
    @Operation(summary = "Get a stored CalendarReference", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<CalendarReferenceDto> getCalendarReference(@PathVariable Long id) {
        LOGGER.info("Get /api/v1/calendar/{}", id);
        try {
            return ResponseEntity.ok(calendarReferenceMapper.calendarReferenceToDto(calendarReferenceService.getFromId(id)));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @Secured("ROLE_USER")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Calendar Reference for the user", security = @SecurityRequirement(name = "apiKey"))
    public void deleteCalendarReference(@PathVariable Long id) {
        LOGGER.info("Deleting Calendar with id: {}", id);
        calendarReferenceService.deleteCalendar(id);
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
    public ResponseEntity<Resource> exportCalendarFile(@PathVariable UUID token) {
        LOGGER.info("Get /api/v1/calendar/get/export/{location}");
        try {
            Calendar reExportedCalendar = pipelineService.pipeCalendar(token);
            byte[] fileContent = reExportedCalendar.toString().getBytes();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + token + ".ics");

            return ResponseEntity.ok()
                                 .headers(headers)
                                 .contentLength(fileContent.length)
                                 .contentType(MediaType.parseMediaType("text/calendar"))
                                 .body(new ByteArrayResource(fileContent));
        } catch (ParserException | IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

}
