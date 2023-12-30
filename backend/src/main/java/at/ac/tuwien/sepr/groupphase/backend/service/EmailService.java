package at.ac.tuwien.sepr.groupphase.backend.service;

public interface EmailService {

    /**
     * Sends a registration email to the specified email address.
     *
     * @param to the email address of the new user
     * @return nothing
     */
    void sendRegistrationEmail(String to);

    /**
     * Sends a reset email to the specified email address.
     *
     * @param to the email address of the user
     * @param resetUrl the reset url
     * @return nothing
     */
    void sendResetEmail(String to, String resetUrl);
}
