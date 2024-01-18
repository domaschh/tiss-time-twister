package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserRegistrationDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.PasswordResetToken;
import at.ac.tuwien.sepr.groupphase.backend.exception.EmailAlreadyExistsException;
import at.ac.tuwien.sepr.groupphase.backend.exception.InvalidEmailException;
import at.ac.tuwien.sepr.groupphase.backend.exception.InvalidPasswordException;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.exception.PasswordDoesNotMatchEmailException;
import at.ac.tuwien.sepr.groupphase.backend.repository.PasswordResetRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepr.groupphase.backend.security.JwtTokenizer;
import at.ac.tuwien.sepr.groupphase.backend.service.EmailService;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CustomUserDetailService implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenizer jwtTokenizer;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LOGGER.debug("Load all user by email");
        try {
            ApplicationUser applicationUser = findApplicationUserByEmail(email);

            List<GrantedAuthority> grantedAuthorities;
            if (applicationUser.getAdmin()) {
                grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
            } else {
                grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");
            }

            return new User(applicationUser.getEmail(), applicationUser.getPassword(), grantedAuthorities);
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
    }

    @Override
    public ApplicationUser findApplicationUserByEmail(String email) {
        LOGGER.debug("Find application user by email");
        ApplicationUser applicationUser = userRepository.findUserByEmail(email);
        if (applicationUser != null) {
            return applicationUser;
        }
        throw new NotFoundException(String.format("Could not find the user with the email address %s", email));
    }

    @Override
    public ApplicationUser findApplicationUserById(Long id) {
        LOGGER.debug("Find application user by id");
        ApplicationUser applicationUser = userRepository.findUserById(id);
        if (applicationUser != null) {
            return applicationUser;
        }
        throw new NotFoundException(String.format("Could not find the user with the id %s", id));
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        try {
            UserDetails userDetails = loadUserByUsername(userLoginDto.getEmail());
            if (userDetails == null
                || !userDetails.isAccountNonExpired()
                || !userDetails.isAccountNonLocked()
                || !userDetails.isCredentialsNonExpired()) {
                throw new NotFoundException("User not found with email: " + userLoginDto.getEmail());
            }

            if (!isValidPassword(userLoginDto.getPassword())) {
                throw new InvalidPasswordException("Password format is invalid");
            }
            if (passwordEncoder.matches(userLoginDto.getPassword(), userDetails.getPassword())) {
                List<String> roles = userDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();
                return jwtTokenizer.getAuthToken(userDetails.getUsername(), roles);
            } else {
                throw new PasswordDoesNotMatchEmailException("Invalid password");
            }
        } catch (UsernameNotFoundException e) {
            LOGGER.error("Authentication failed for user: " + userLoginDto.getEmail(), e);
            throw new NotFoundException("User not found with email: " + userLoginDto.getEmail());
        }
    }

    @Override
    public String registerUser(UserRegistrationDto userRegistrationDto) throws EmailAlreadyExistsException, InvalidEmailException, InvalidPasswordException {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");

        if (userRepository.findUserByEmail(userRegistrationDto.getEmail()) != null) {
            throw new EmailAlreadyExistsException("An user already exists with this email.");
        }

        if (!isValidEmail(userRegistrationDto.getEmail())) {
            throw new InvalidEmailException("Email entered is not valid.");
        }

        if (!isValidPassword(userRegistrationDto.getPassword())) {
            throw new InvalidPasswordException("Password entered is not valid.");
        }
        ApplicationUser newUser = new ApplicationUser(
            userRegistrationDto.getEmail(),
            passwordEncoder.encode(userRegistrationDto.getPassword()),
            false
        );
        userRepository.save(newUser);
        return jwtTokenizer.getAuthToken(userRegistrationDto.getEmail(), roles);
    }

    @Override
    @Transactional
    public void sendPasswordResetEmail(String email, String appUrl) {
        ApplicationUser user = findApplicationUserByEmail(email);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            createPasswordResetTokenForUser(user, token);
            emailService.sendPasswordResetEmail(user, token, appUrl);
        } else {
            LOGGER.error("User not found for email: {}", email);
            throw new NotFoundException("User not found with email: " + email);
        }
    }

    @Override
    public Optional<ApplicationUser> getUserByPasswordResetToken(String token) {
        PasswordResetToken resetToken = getPasswordResetToken(token);
        if (resetToken != null) {
            return Optional.ofNullable(resetToken.getUser());
        }
        return Optional.empty();
    }

    @Override
    public void changeUserPassword(ApplicationUser user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void createPasswordResetTokenForUser(final ApplicationUser user, final String token) {
        ApplicationUser managedUser = userRepository.findUserByEmail(user.getEmail());
        if (managedUser == null) {
            throw new NotFoundException("User not found with email: " + user.getEmail());
        }
        final PasswordResetToken myToken = new PasswordResetToken(token, managedUser);
        passwordResetRepository.save(myToken);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordResetRepository.findByToken(token);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = getPasswordResetToken(token);
        if (passToken == null) {
            return "invalidToken";
        } else if (isTokenExpired(passToken)) {
            return "expired";
        }
        return null;
    }

    /**
     * Scheduled task to remove expired reset tokens once every hour from the database.
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void removeExpiredPasswordResetTokens() {
        LOGGER.info("Running scheduled task to remove expired password reset tokens");
        passwordResetRepository.deleteAllExpiredSince(Instant.now());
    }

    private boolean isValidToken(PasswordResetToken token) {
        return token != null && !isTokenExpired(token);
    }

    private boolean isTokenExpired(PasswordResetToken token) {
        if (token == null || token.getExpiryDate() == null) {
            return true;
        }
        return token.getExpiryDate().isBefore(Instant.now());
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
            + "[a-zA-Z0-9_+&*-]+)*@"
            + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
            + "A-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
