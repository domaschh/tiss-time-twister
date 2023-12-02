package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.CalendarReferenceDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.CalendarReferenceMapper;
import at.ac.tuwien.sepr.groupphase.backend.service.CalendarReferenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @PutMapping("/import/url")
    @Operation(summary = "Import a CalendarReference from a URL", security = @SecurityRequirement(name = "apiKey"))
    public CalendarReferenceDto importCalendar(CalendarReferenceDto calendarReferenceDto) throws MalformedURLException {
        LOGGER.info("Put /api/v1/calendar/import/url body:{}", calendarReferenceDto);
        return calendarReferenceMapper.calendarReferenceToDto(
            calendarReferenceService.add(
                calendarReferenceMapper.dtoToCalendarReference(calendarReferenceDto)));
    }

    @Secured("ROLE_USER")
    @GetMapping("/import/url")
    @Operation(summary = "Import a CalendarReference from a URL", security = @SecurityRequirement(name = "apiKey"))
    public CalendarReferenceDto exportCalendar(Long id) {
        LOGGER.info("Get /api/v1/calendar/import/url id:{}", id);
        return calendarReferenceMapper.calendarReferenceToDto(calendarReferenceService.getFromId(id));
    }
}
