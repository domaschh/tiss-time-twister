package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;

public interface EmailService {

    /**
     * Sends a registration email to the specified email address.
     *
     * @param to the email address of the new user
     */
    void sendRegistrationEmail(String to);

    /**
     * Sends a reset password email to the user with a valid token.
     *
     * @param user the user that wants to reset their password
     * @param token the reset token
     * @param appUrl the url
     */
    void sendPasswordResetEmail(ApplicationUser user, String token, String appUrl);
}
