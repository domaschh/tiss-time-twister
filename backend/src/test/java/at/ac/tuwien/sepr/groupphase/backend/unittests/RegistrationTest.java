package at.ac.tuwien.sepr.groupphase.backend.unittests;

import at.ac.tuwien.sepr.groupphase.backend.datagenerator.UserDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.RegistrationEndpoint;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserRegistrationDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.exception.EmailAlreadyExistsException;
import at.ac.tuwien.sepr.groupphase.backend.exception.InvalidEmailException;
import at.ac.tuwien.sepr.groupphase.backend.exception.InvalidPasswordException;
import at.ac.tuwien.sepr.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test", "generateData"})
public class RegistrationTest {

    @Autowired
    private RegistrationEndpoint registrationEndpoint;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDataGenerator userDataGenerator;
    @Autowired
    private UserService userService;
    private UserRegistrationDto newUser;
    private final String SAMPLE_EMAIL = "user3@email.com";
    private final String SAMPLE_PASSWORD = "password";

    @BeforeEach
    public void setUp() {
        newUser = new UserRegistrationDto("user3@email.com", "password");
    }

    @Test
    public void whenRegisterCalled_Successful_Endpoint() {
        String expectedResponse = "User registered successfully";
        ResponseEntity<String> response = registrationEndpoint.registerUser(newUser);
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void whenRegisterCalled_Successful_Service() {
        userService.registerUser(newUser);
        ApplicationUser registeredUser = userRepository.findUserByEmail(newUser.getEmail());
        assertNotNull(registeredUser);
        assertEquals(newUser.getEmail(), registeredUser.getEmail());
    }

    @Test
    public void whenEmailAlreadyExists_ThrowsException() {
        userService.registerUser(newUser);
        assertThrows(EmailAlreadyExistsException.class, () -> {
            userService.registerUser(newUser);
        });
    }

    @Test
    public void whenRegisterCalled_WithInvalidEmail_ThrowsInvalidEmailException() {
        UserRegistrationDto invalidEmailUser = new UserRegistrationDto("invalidemail", "ValidPassword1!");
        assertThrows(InvalidEmailException.class, () -> {
            userService.registerUser(invalidEmailUser);
        });
    }

    @Test
    public void whenRegisterCalled_WithInvalidPassword_ThrowsInvalidPasswordException() {
        UserRegistrationDto invalidPasswordUser = new UserRegistrationDto("validemail@email.com", "wrong");
        assertThrows(InvalidPasswordException.class, () -> {
            userService.registerUser(invalidPasswordUser);
        });
    }
}
