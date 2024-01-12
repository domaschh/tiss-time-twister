package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.TagDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.TagMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.service.ExtractUsernameService;
import at.ac.tuwien.sepr.groupphase.backend.service.TagService;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/tag")
public class TagEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final TagService tagService;
    private final TagMapper tagMapper;
    private final ExtractUsernameService extractUsernameService;
    private final UserService userService;

    public TagEndpoint(TagService tagService, TagMapper tagMapper, ExtractUsernameService extractUsernameService, UserService userService) {
        this.tagService = tagService;
        this.tagMapper = tagMapper;
        this.extractUsernameService = extractUsernameService;
        this.userService = userService;
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Get all stored tags", security = @SecurityRequirement(name = "apiKey"))
    public List<TagDto> getAllTagsForUser(HttpServletRequest request) {
        String username = extractUsernameService.getUsername(request);
        LOGGER.info("Get /api/v1/tag/{}", username);
        return tagService.getTagsForUser(username).stream().map(tagMapper::tagToDto).toList();
    }

    @Secured("ROLE_USER")
    @PostMapping
    @Operation(summary = "Create a new tag", security = @SecurityRequirement(name = "apiKey"))
    public TagDto createTag(@Valid @RequestBody TagDto tag, HttpServletRequest request) {
        LOGGER.info("POST /api/v1/tag/body:{}", tag);
        String username = extractUsernameService.getUsername(request);
        ApplicationUser user = userService.findApplicationUserByEmail(username);
        return tagMapper.tagToDto(tagService.add(tagMapper.dtoToTag(tag, user)));
    }

    @Secured("ROLE_USER")
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a tag by id", security = @SecurityRequirement(name = "apiKey"))
    public void deleteTagById(@PathVariable Long id) {
        LOGGER.info("DELETE /api/v1/tag/{}", id);
        tagService.delete(id);
    }
}
