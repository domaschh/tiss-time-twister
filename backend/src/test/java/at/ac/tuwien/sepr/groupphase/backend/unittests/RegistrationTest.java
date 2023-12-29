package at.ac.tuwien.sepr.groupphase.backend.unittests;

import at.ac.tuwien.sepr.groupphase.backend.basetest.TestData;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test", "generateData"})
public class RegistrationTest implements TestData {

    @Autowired
    private RegistrationEndpoint registrationEndpoint;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDataGenerator userDataGenerator;
    @Autowired
    private UserService userService;
    private UserRegistrationDto newUserDto;
    private final String SAMPLE_EMAIL = "sample@email.com";
    private final String SAMPLE_PASSWORD = "1Password";

    @BeforeEach
    public void setUp() {
        userDataGenerator.generateUsers();
        newUserDto = new UserRegistrationDto();
        newUserDto.setEmail(SAMPLE_EMAIL);
        newUserDto.setPassword(SAMPLE_PASSWORD);
    }

    @Test
    public void whenRegisterCalled_Successful_Endpoint() {
        ResponseEntity<Object> response = registrationEndpoint.registerUser(newUserDto);
        assertNotNull(response);
        assertTrue(response.getBody() instanceof String);
    }

    @Test
    public void whenRegisterCalled_Successful_Service() {
        ApplicationUser registeredUser = userRepository.findUserByEmail(newUserDto.getEmail());
        assertNotNull(registeredUser);
        assertEquals(newUserDto.getEmail(), registeredUser.getEmail());
    }

    @Test
    public void whenEmailAlreadyExists_ThrowsException() {
        userService.registerUser(newUserDto);
        assertThrows(EmailAlreadyExistsException.class, () -> {
            userService.registerUser(newUserDto);
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
