package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.RuleDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.RuleMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.Configuration;
import at.ac.tuwien.sepr.groupphase.backend.service.RuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/rule")
public class RuleEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final RuleService ruleService;
    private final RuleMapper ruleMapper;


    @Autowired
    public RuleEndpoint(RuleService ruleService, RuleMapper ruleMapper) {
        this.ruleService = ruleService;
        this.ruleMapper = ruleMapper;
    }

    @Secured("ROLE_USER")
    @PutMapping
    @Operation(summary = "add a Rule", security = @SecurityRequirement(name = "apiKey"))
    public RuleDto add(@RequestBody RuleDto ruleDto) {
        LOGGER.info("Put /api/v1/rule/body: {}", ruleDto);
        return ruleMapper.ruleToDto(ruleService.add(ruleMapper.dtoToRule(ruleDto)));
    }

    @Secured("ROLE_USER")
    @GetMapping(value = "/{id}")
    @Operation(summary = "get a Rule by Id", security = @SecurityRequirement(name = "apiKey"))
    public RuleDto getById(@RequestBody Long id) {
        LOGGER.info("Get /api/v1/rule/id: {}", id);
        return ruleMapper.ruleToDto(ruleService.getById(id));
    }

    @Secured("ROLE_USER")
    @GetMapping(value = "/{configuration}")
    @Operation(summary = "get all Rules within a Configuration", security = @SecurityRequirement(name = "apiKey"))
    public List<RuleDto> getAllByConfiguration(@RequestBody Configuration config) {
        LOGGER.info("Get /api/v1/rule/configuration: {}", config);
        return ruleService.getAllByConfiguration(config).stream().map(ruleMapper::ruleToDto).toList();
    }
}
