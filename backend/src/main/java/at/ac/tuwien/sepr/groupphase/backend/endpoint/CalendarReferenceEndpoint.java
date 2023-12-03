package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.CalendarReferenceDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.CalendarReferenceMapper;
import at.ac.tuwien.sepr.groupphase.backend.service.CalendarReferenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;

@RestController
@RequestMapping(value = "/api/v1/calendar")
public class CalendarReferenceEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CalendarReferenceService calendarReferenceService;
    private final CalendarReferenceMapper calendarReferenceMapper;

    @Autowired
    public CalendarReferenceEndpoint(CalendarReferenceService calendarReferenceService, CalendarReferenceMapper calendarReferenceMapper) {
        this.calendarReferenceService = calendarReferenceService;
        this.calendarReferenceMapper = calendarReferenceMapper;
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
        LOGGER.info("Get /api/v1/calendar/get/{}", id);
        return calendarReferenceMapper.calendarReferenceToDto(calendarReferenceService.getFromId(id));
    }
}
