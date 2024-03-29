package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserRegistrationDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.PasswordResetToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    /**
     * Find a user in the context of Spring Security based on the email address.
     *
     * @param email the email address
     * @return a Spring Security user
     * @throws UsernameNotFoundException is thrown if the specified user does not exist
     */
    @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    /**
     * Find an application user based on the email address.
     *
     * @param email the email address
     * @return an application user
     */
    ApplicationUser findApplicationUserByEmail(String email);

    /**
     * Find an application user based on the id.
     *
     * @param id the id
     * @return an application user
     */
    ApplicationUser findApplicationUserById(Long id);

    /**
     * Log in a user.
     *
     * @param userLoginDto login credentials
     * @return the JWT, if successful
     * @throws org.springframework.security.authentication.BadCredentialsException if credentials are bad
     */
    String login(UserLoginDto userLoginDto);

    /**
     * Register a user.
     *
     * @param userRegistrationDto registration credentials
     * @return the JWT, if successful
     * @throws org.springframework.security.authentication.BadCredentialsException if credentials are bad
     */
    String registerUser(UserRegistrationDto userRegistrationDto);

    /**
     * Sends an email to reset the password of the user.
     *
     * @param email the email to reset the password
     */

    void sendPasswordResetEmail(String email);

    Optional<ApplicationUser> getUserByPasswordResetToken(String token);


    void changeUserPassword(ApplicationUser user, String password);

    void createPasswordResetTokenForUser(ApplicationUser user, String token);

    PasswordResetToken getPasswordResetToken(String token);

    String validatePasswordResetToken(String token);
}
