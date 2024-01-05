package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.CalendarReferenceDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.ConfigurationDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.CalendarReferenceMapper;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.ConfigurationMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.CalendarReference;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.service.CalendarReferenceService;
import at.ac.tuwien.sepr.groupphase.backend.service.ExtractUsernameService;
import at.ac.tuwien.sepr.groupphase.backend.service.PipelineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/calendar")
public class CalendarReferenceEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CalendarReferenceService calendarReferenceService;
    private final CalendarReferenceMapper calendarReferenceMapper;
    private final PipelineService pipelineService;
    private final ExtractUsernameService extractUsernameService;
    private final ConfigurationMapper configurationMapper;

    @Autowired
    public CalendarReferenceEndpoint(CalendarReferenceService calendarReferenceService, CalendarReferenceMapper calendarReferenceMapper,
                                     PipelineService pipelineService, ExtractUsernameService extractUsernameService,
                                     ConfigurationMapper configurationMapper) {
        this.calendarReferenceService = calendarReferenceService;
        this.calendarReferenceMapper = calendarReferenceMapper;
        this.pipelineService = pipelineService;
        this.extractUsernameService = extractUsernameService;
        this.configurationMapper = configurationMapper;
    }

    @Secured("ROLE_USER")
    @PutMapping
    @Operation(summary = "Import a CalendarReference", security = @SecurityRequirement(name = "apiKey"))
    public CalendarReferenceDto importCalendarReference(@RequestBody CalendarReferenceDto calendarReferenceDto,
                                                        HttpServletRequest request) {
        String username = extractUsernameService.getUsername(request);
        LOGGER.info("Put /api/v1/calendar/body:{}", calendarReferenceDto);
        return calendarReferenceMapper.calendarReferenceToDto(
            calendarReferenceService.add(
                calendarReferenceMapper.dtoToCalendarReference(calendarReferenceDto), username));
    }

    @Secured("ROLE_USER")
    @PutMapping("/file")
    @Operation(summary = "Import a CalendarReference with File", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<?> uploadICalFile(@RequestParam("name") String name,
                                            @RequestParam("file") MultipartFile file,
                                            @RequestParam(required = false) UUID token,
                                            HttpServletRequest request) {
        try {
            String username = extractUsernameService.getUsername(request);
            CalendarReference savedCalendarReference = calendarReferenceService.addFile(name, file, username, token);
            return ResponseEntity.ok(calendarReferenceMapper.calendarReferenceToDto(savedCalendarReference));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Get all stored CalendarReference", security = @SecurityRequirement(name = "apiKey"))
    public List<CalendarReferenceDto> getAllForUser(HttpServletRequest request) {
        String username = extractUsernameService.getUsername(request);
        LOGGER.info("Get /api/v1/calendar/{}", username);
        return calendarReferenceService.getAllForUser(username).stream().map(calendarReferenceMapper::calendarReferenceToDto).toList();
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

    @Secured("ROLE_USER")
    @PostMapping("/{calendarId}/{configId}")
    @Operation(summary = "Add a public config to a CalendarReference", security = @SecurityRequirement(name = "apiKey"))
    public CalendarReferenceDto addConfig(@PathVariable Long calendarId, @PathVariable Long configId) {
        LOGGER.info("Adding Config with id {} to Calendar with id: {}", configId, calendarId);
        return calendarReferenceMapper.calendarReferenceToDto(calendarReferenceService.addConfig(configId, calendarId));
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/{calendarId}/{configId}")
    @Operation(summary = "Remove a public config from a CalendarReference", security = @SecurityRequirement(name = "apiKey"))
    public CalendarReferenceDto removeConfig(@PathVariable Long calendarId, @PathVariable Long configId) {
        LOGGER.info("Adding Config with id {} to Calendar with id: {}", configId, calendarId);
        return calendarReferenceMapper.calendarReferenceToDto(calendarReferenceService.removeConfig(configId, calendarId));
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
        LOGGER.info("Get /api/v1/calendar/get/export/{token}");
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
            return ResponseEntity.internalServerError().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Secured("ROLE_USER")
    @PostMapping("/preview/{id}")
    @Operation(summary = "Export a calender from its url")
    public ResponseEntity<Resource> exportCalendarFile(@PathVariable Long id, @RequestBody List<ConfigurationDto> configurationDtos) {
        LOGGER.info("Get /api/v1/calendar/get/preview/{}, body: {}", id, configurationDtos);
        try {
            Calendar preview = pipelineService.previewConfiguration(id,
                                                                    configurationDtos.stream().map(configurationMapper::toEntity).toList());
            byte[] fileContent = preview.toString().getBytes();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= preview[" + id + "].ics");

            return ResponseEntity.ok()
                                 .headers(headers)
                                 .contentLength(fileContent.length)
                                 .contentType(MediaType.parseMediaType("text/calendar"))
                                 .body(new ByteArrayResource(fileContent));
        } catch (ParserException | IOException | URISyntaxException e) {
            return ResponseEntity.internalServerError().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
