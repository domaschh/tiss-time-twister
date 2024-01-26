package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepr.groupphase.backend.service.EventService;
import at.ac.tuwien.sepr.groupphase.backend.service.ExtractUsernameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(value = "/api/v1/event")
public class EventEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EventService eventService;
    private final EventMapper eventMapper;
    private final ExtractUsernameService extractUsernameService;


    @Autowired
    public EventEndpoint(EventService eventService, EventMapper eventMapper, ExtractUsernameService extractUsernameService) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
        this.extractUsernameService = extractUsernameService;
    }

    @Secured("ROLE_USER")
    @PostMapping
    @Operation(summary = "Create a new event", security = @SecurityRequirement(name = "apiKey"))
    public EventDto createEvent(@RequestBody EventDto event, HttpServletRequest request) {
        LOGGER.info("POST /api/v1/event/body:{}", event);
        String username = extractUsernameService.getUsername(request);
        return eventMapper.eventToDto(eventService.add(eventMapper.dtoToEvent(event, username)));
    }

    @Secured("ROLE_USER")
    @GetMapping(value = "/{id}")
    @Operation(summary = "Get an event by id", security = @SecurityRequirement(name = "apiKey"))
    public EventDto getEventById(@PathVariable Long id) {
        LOGGER.info("GET /api/v1/event/{}", id);
        return eventMapper.eventToDto(eventService.getEventById(id));
    }

    @Secured("ROLE_USER")
    @PutMapping(value = "/{id}")
    @Operation(summary = "Update an event by id", security = @SecurityRequirement(name = "apiKey"))
    public EventDto updateEvent(@PathVariable Long id, @RequestBody EventDto event, HttpServletRequest request) {
        LOGGER.info("PUT /api/v1/event/{} body:{}", id, event);
        String username = extractUsernameService.getUsername(request);
        return eventMapper.eventToDto(eventService.updateEvent(id, eventMapper.dtoToEvent(event, username)));
    }

    @Secured("ROLE_USER")
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete an event by id", security = @SecurityRequirement(name = "apiKey"))
    public void deleteEventById(@PathVariable Long id) {
        LOGGER.info("DELETE /api/v1/event/{}", id);
        eventService.delete(id);
    }
}
