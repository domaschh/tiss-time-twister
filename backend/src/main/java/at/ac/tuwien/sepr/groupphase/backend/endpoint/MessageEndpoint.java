package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.DetailedMessageDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.MessageInquiryDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.SimpleMessageDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.MessageMapper;
import at.ac.tuwien.sepr.groupphase.backend.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/messages")
public class MessageEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @Autowired
    public MessageEndpoint(MessageService messageService, MessageMapper messageMapper) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Get list of messages without details", security = @SecurityRequirement(name = "apiKey"))
    public List<SimpleMessageDto> findAll() {
        LOGGER.info("GET /api/v1/messages");
        return messageMapper.messageToSimpleMessageDto(messageService.findAll());
    }

    @Secured("ROLE_USER")
    @GetMapping(value = "/{id}")
    @Operation(summary = "Get detailed information about a specific message", security = @SecurityRequirement(name = "apiKey"))
    public DetailedMessageDto find(@PathVariable Long id) {
        LOGGER.info("GET /api/v1/messages/{}", id);
        return messageMapper.messageToDetailedMessageDto(messageService.findOne(id));
    }

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Publish a new message", security = @SecurityRequirement(name = "apiKey"))
    public DetailedMessageDto create(@Valid @RequestBody MessageInquiryDto messageDto) {
        LOGGER.info("POST /api/v1/messages body: {}", messageDto);
        return messageMapper.messageToDetailedMessageDto(
            messageService.publishMessage(messageMapper.messageInquiryDtoToMessage(messageDto)));
    }
}
