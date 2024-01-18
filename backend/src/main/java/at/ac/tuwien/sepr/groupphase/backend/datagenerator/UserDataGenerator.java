package at.ac.tuwien.sepr.groupphase.backend.datagenerator;

import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
    private static final String USER_PASSWORD = "Password1";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDataGenerator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void generateUsers() {
        LOGGER.debug("Generating {} user entries", NUMBER_OF_USERS_TO_GENERATE + NUMBER_OF_ADMINS_TO_GENERATE);
        List<ApplicationUser> applicationUsers = new ArrayList<>();
        final String encryptedPassword = passwordEncoder.encode(USER_PASSWORD);
        int i = 1;
        // Generating admins
        for (; i <= NUMBER_OF_ADMINS_TO_GENERATE; i++) {
            ApplicationUser e = new ApplicationUser(String.format(ADMIN_EMAIL_PATTERN, i), encryptedPassword, true, (long) i, List.of());
            applicationUsers.add(e);
        }

        // Generating users
        int j = i;
        for (; i <= j + NUMBER_OF_USERS_TO_GENERATE; i++) {
            ApplicationUser e = new ApplicationUser(String.format(USER_EMAIL_PATTERN, i), encryptedPassword, false, (long) i, List.of());
            applicationUsers.add(e);
        }

        userRepository.saveAll(applicationUsers);
        LOGGER.debug("Saved all application users");
    }
}