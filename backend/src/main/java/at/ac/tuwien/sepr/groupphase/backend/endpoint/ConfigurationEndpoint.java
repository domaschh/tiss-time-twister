package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.ConfigurationDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.ConfigurationMapper;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.service.ConfigurationService;
import at.ac.tuwien.sepr.groupphase.backend.service.ExtractUsernameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * This endpoint is used for kubernetes health checks.
 */
@RestController
@RequestMapping("/api/v1/configuration")
public class ConfigurationEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ConfigurationService configurationService;
    private final ConfigurationMapper configurationMapper;
    private final ExtractUsernameService extractUsernameService;

    @Autowired
    public ConfigurationEndpoint(ConfigurationService configurationService, ConfigurationMapper configurationMapper,
                                 ExtractUsernameService extractUsernameService) {
        this.configurationService = configurationService;
        this.configurationMapper = configurationMapper;
        this.extractUsernameService = extractUsernameService;
    }

    @Secured("ROLE_USER")
    @PutMapping
    @Operation(summary = "Create a Configuration", security = @SecurityRequirement(name = "apiKey"))
    public ConfigurationDto createConfiguration(@RequestBody ConfigurationDto configurationDto, HttpServletRequest request) {
        String username = extractUsernameService.getUsername(request);
        LOGGER.info("Put /api/v1/configuration/body:{}", configurationDto);
        return configurationMapper.toDto(
            configurationService.add(configurationMapper.toEntity(configurationDto), username));
    }

    @Secured("ROLE_USER")
    @PutMapping("/changeVisibility/{configurationId}")
    @Operation(summary = "Create a Configuration", security = @SecurityRequirement(name = "apiKey"))
    public ConfigurationDto changeVisibility(@PathVariable Long configurationId) {
        LOGGER.info("Put /api/v1/configuration/changeVisibility/{}", configurationId);
        return configurationMapper.toDto(
            configurationService.changeVisibility(configurationId));
    }

    @Secured("ROLE_USER")
    @GetMapping("/{id}")
    @Operation(summary = "Get a stored Configuration", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<ConfigurationDto> getConfiguration(@PathVariable Long id) {
        LOGGER.info("Get /api/v1/configuration/{}", id);
        try {
            return ResponseEntity.ok(configurationMapper.toDto(configurationService.getById(id)));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @Secured("ROLE_USER")
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a Configuration", security = @SecurityRequirement(name = "apiKey"))
    public void deleteConfiguration(@PathVariable Long id) {
        LOGGER.info("Deleting Configuration with id: {}", id);
        configurationService.delete(id);
    }


    @PermitAll
    @GetMapping("/allPublic")
    @Operation(summary = "Get all published Configurations")
    public List<ConfigurationDto> getAllPublished() {
        LOGGER.info("Get All published Configurations");
        return configurationMapper.toDtoList(configurationService.getAllPublicConfigurations());
    }

    @Secured("ROLE_USER")
    @GetMapping("/all")
    @Operation(summary = "Get all by user Configuration", security = @SecurityRequirement(name = "apiKey"))
    public List<ConfigurationDto> getAllForUser(HttpServletRequest request) {
        String username = extractUsernameService.getUsername(request);
        LOGGER.info("Get /api/v1/configuration/all/{}", username);
        return configurationService.getAllByUser(username).stream().map(configurationMapper::toDto).toList();
    }
}