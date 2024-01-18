package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.PasswordResetDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1")
public class PasswordResetEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService;

    @Autowired
    public PasswordResetEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PermitAll
    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPasswordProcess(@RequestParam("email") final String email) {
        try {
            userService.sendPasswordResetEmail(email);
            LOGGER.info("Reset password email sent to: {}", email);
            return ResponseEntity.ok(Map.of("message", "Reset password email sent."));
        } catch (Exception e) {
            LOGGER.error("Error sending email: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email.");
        }
    }

    @PermitAll
    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody PasswordResetDto passwordResetDto) {
        final String result = userService.validatePasswordResetToken(passwordResetDto.getToken());
        if (result != null) {
            return ResponseEntity.badRequest().body("Error: " + result);
        }
        LOGGER.info("Received password reset DTO: {}", passwordResetDto);
        Optional<ApplicationUser> user = userService.getUserByPasswordResetToken(passwordResetDto.getToken());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Invalid token.");
        }

        if (!passwordResetDto.getNewPassword().equals(passwordResetDto.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Error: Passwords do not match.");
        }
        userService.changeUserPassword(user.get(), passwordResetDto.getNewPassword());
        return ResponseEntity.ok(Map.of("message","Your password has been successfully reset."));
    }
}
