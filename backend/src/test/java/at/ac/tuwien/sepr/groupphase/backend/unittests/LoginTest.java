package at.ac.tuwien.sepr.groupphase.backend.unittests;

import at.ac.tuwien.sepr.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepr.groupphase.backend.datagenerator.UserDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.LoginEndpoint;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.exception.InvalidPasswordException;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.exception.PasswordDoesNotMatchEmailException;
import at.ac.tuwien.sepr.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test", "generateData"})
public class LoginTest implements TestData {

    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", postgres::getJdbcUrl);
        propertyRegistry.add("spring.datasource.username", postgres::getUsername);
        propertyRegistry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private LoginEndpoint loginEndpoint;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDataGenerator userDataGenerator;
    @Autowired
    private UserService userService;
    private UserLoginDto userLoginDto;
    private final String sampleEmail = "user2@email.com";
    private final String samplePwd = "Password1";

    @BeforeEach
    public void setUp() {
        userDataGenerator.generateUsers();
        userLoginDto = new UserLoginDto();
        userLoginDto.setEmail(sampleEmail);
        userLoginDto.setPassword(samplePwd);
    }

    @Test
    public void whenLoginCalled_Successful_Endpoint() {
        String response = loginEndpoint.login(userLoginDto);
        assertNotNull(response);
        assertTrue(response.startsWith("Bearer "));
    }

    @Test
    public void whenLoginCalled_Unsuccessful_Endpoint() {
        UserLoginDto incorrectLoggedInUser = new UserLoginDto();
        incorrectLoggedInUser.setEmail("wrongEmail");
        incorrectLoggedInUser.setPassword("WrongPassword");
        assertThrows(NotFoundException.class, () -> {
            loginEndpoint.login(incorrectLoggedInUser);
        });
    }

    @Test
    public void whenLoginCalled_Successful_Service() {
        String response = userService.login(userLoginDto);
        assertNotNull(response);
        assertTrue(response.startsWith("Bearer "));
    }

    @Test
    public void whenLoginCalled_Unsuccessful_Service() {
        UserLoginDto incorrectLoggedInUser = new UserLoginDto();
        incorrectLoggedInUser.setEmail(sampleEmail);
        incorrectLoggedInUser.setPassword("WrongPassword1");
        assertThrows(PasswordDoesNotMatchEmailException.class, () -> {
            userService.login(incorrectLoggedInUser);
        });
    }

    @Test
    public void whenUserFoundByEmail_Successful() {
        String email = "user2@email.com";
        ApplicationUser user = userRepository.findUserByEmail(email);
        assertNotNull(user);
        assertEquals(email, user.getEmail());
    }

    @Test
    public void whenLoginCalled_WithNonexistentEmail_ThrowsUsernameNotFoundException() {
        UserLoginDto nonexistentUser = new UserLoginDto();
        nonexistentUser.setEmail("nonexistent@email.com");
        nonexistentUser.setPassword(samplePwd);
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(nonexistentUser.getEmail());
        });
    }

    @Test
    public void whenLoginCalled_WithIncorrectPassword_ThrowsPasswordDoesNotMatchEmailException() {
        UserLoginDto incorrectPasswordUser = new UserLoginDto();
        incorrectPasswordUser.setEmail(sampleEmail);
        incorrectPasswordUser.setPassword("WrongPassword1");
        assertThrows(PasswordDoesNotMatchEmailException.class, () -> {
            userService.login(incorrectPasswordUser);
        });
    }

    @Test
    public void whenLoginCalled_WithInvalidPasswordFormat_ThrowsInvalidPasswordException() {
        UserLoginDto invalidPasswordFormatUser = new UserLoginDto();
        invalidPasswordFormatUser.setEmail(sampleEmail);
        invalidPasswordFormatUser.setPassword("wrong");
        assertThrows(InvalidPasswordException.class, () -> {
            userService.login(invalidPasswordFormatUser);
        });
    }
}
