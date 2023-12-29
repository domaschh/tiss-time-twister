package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserRegistrationDto;
import at.ac.tuwien.sepr.groupphase.backend.exception.EmailAlreadyExistsException;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import jakarta.annotation.security.PermitAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(value = "/api/v1/register")
public class RegistrationEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService;

    public RegistrationEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PermitAll
    @PostMapping("")
    public ResponseEntity<Object> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        try {
            String token = userService.registerUser(userRegistrationDto);
            return ResponseEntity.ok(token);
        } catch (EmailAlreadyExistsException e) {
            LOGGER.error("Registration failed: {}", e.getMessage());
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
        }
    }
}
