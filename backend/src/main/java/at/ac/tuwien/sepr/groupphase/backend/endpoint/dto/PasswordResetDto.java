package at.ac.tuwien.sepr.groupphase.backend.endpoint.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
public class PasswordResetDto {

    @NotNull(message = "New password cannot be null")
    @NotEmpty(message = "New password cannot be empty")
    private String newPassword;

    @NotNull(message = "Confirm password cannot be null")
    @NotEmpty(message = "Confirm password cannot be empty")
    private String confirmPassword;

    @NotNull(message = "Reset token cannot be null")
    @NotEmpty(message = "Reset token cannot be empty")
    private String token;

}
