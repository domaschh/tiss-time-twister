package at.ac.tuwien.sepr.groupphase.backend.datagenerator;

import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Profile("generateData")
@Component
public class UserDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int NUMBER_OF_USERS_TO_GENERATE = 5;
    private static final int NUMBER_OF_ADMINS_TO_GENERATE = 1;
    private static final String USER_EMAIL_PATTERN = "user%d@email.com";
    private static final String ADMIN_EMAIL_PATTERN = "admin%d@email.com";
    private static final String USER_PASSWORD = "password";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDataGenerator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void generateUsers() {
        LOGGER.debug("Generating {} user entries", NUMBER_OF_USERS_TO_GENERATE + NUMBER_OF_ADMINS_TO_GENERATE);
        List<ApplicationUser> applicationUsers = new ArrayList<>();
        final String ENCRYPTED_PASSWORD = passwordEncoder.encode(USER_PASSWORD);

        // Generating admins
        for (int i = 1; i <= NUMBER_OF_ADMINS_TO_GENERATE; i++) {
            applicationUsers.add(new ApplicationUser(String.format(ADMIN_EMAIL_PATTERN, i), ENCRYPTED_PASSWORD, true));
        }

        // Generating users
        for (int i = 1; i <= NUMBER_OF_USERS_TO_GENERATE; i++) {
            applicationUsers.add(new ApplicationUser(String.format(USER_EMAIL_PATTERN, i), ENCRYPTED_PASSWORD, false));
        }

        userRepository.saveAll(applicationUsers);
        LOGGER.debug("Saved all application users");
    }
}