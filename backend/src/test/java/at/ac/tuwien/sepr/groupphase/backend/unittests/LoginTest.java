package at.ac.tuwien.sepr.groupphase.backend.unittests;

import at.ac.tuwien.sepr.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepr.groupphase.backend.datagenerator.UserDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.LoginEndpoint;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.exception.InvalidPasswordException;
import at.ac.tuwien.sepr.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test", "generateData"})
public class LoginTest implements TestData {

    @Autowired
    private LoginEndpoint loginEndpoint;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDataGenerator userDataGenerator;
    @Autowired
    private UserService userService;
    private UserLoginDto userLoginDto;
    private final String SAMPLE_EMAIL = "user2@email.com";
    private final String SAMPLE_PASSWORD = "password";

    @BeforeEach
    public void setUp() {
        userDataGenerator.generateUsers();
        userLoginDto = new UserLoginDto();
        userLoginDto.setEmail(SAMPLE_EMAIL);
        userLoginDto.setPassword(SAMPLE_PASSWORD);
    }

    @Test
    public void whenLoginCalled_Successfull_Endpoint(){
        String expectedResponse = "Success";
        String response = loginEndpoint.login(userLoginDto);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void whenLoginCalled_Successfull_Service(){
        String expectedResponse = "Success";
        String response = userService.login(userLoginDto);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void whenUserFoundByEmail_Successfull(){
        String email = "user2@email.com";
        ApplicationUser user = userRepository.findUserByEmail(email);
        assertNotNull(user);
        assertEquals(email, user.getEmail());
    }

    @Test
    public void whenLoginCalled_WithNonexistentEmail_ThrowsUsernameNotFoundException() {
        UserLoginDto nonexistentUser = new UserLoginDto();
        nonexistentUser.setEmail("nonexistent@email.com");
        nonexistentUser.setPassword(SAMPLE_PASSWORD);

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(nonexistentUser.getEmail());
        });
    }

    @Test
    public void whenLoginCalled_WithIncorrectPassword_ThrowsBadCredentialsException() {
        UserLoginDto incorrectPasswordUser = new UserLoginDto();
        incorrectPasswordUser.setEmail(SAMPLE_EMAIL);
        incorrectPasswordUser.setPassword("WrongPassword");

        assertThrows(BadCredentialsException.class, () -> {
            userService.login(incorrectPasswordUser);
        });
    }

    @Test
    public void whenLoginCalled_WithInvalidPasswordFormat_ThrowsInvalidPasswordException() {
        UserLoginDto invalidPasswordFormatUser = new UserLoginDto();
        invalidPasswordFormatUser.setEmail(SAMPLE_EMAIL);
        invalidPasswordFormatUser.setPassword("wrong");

        assertThrows(InvalidPasswordException.class, () -> {
            userService.login(invalidPasswordFormatUser);
        });
    }
}
