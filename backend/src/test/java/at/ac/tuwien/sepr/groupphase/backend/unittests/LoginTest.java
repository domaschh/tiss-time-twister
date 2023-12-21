package at.ac.tuwien.sepr.groupphase.backend.unittests;

import at.ac.tuwien.sepr.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepr.groupphase.backend.datagenerator.UserDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.LoginEndpoint;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    private final String sampleEmail = "user@example.com";
    private final String samplePassword = "password";

    @BeforeEach
    public void setUp() {
        userDataGenerator.generateUsers();
        userLoginDto = new UserLoginDto();
        userLoginDto.setEmail(sampleEmail);
        userLoginDto.setPassword(samplePassword);
    }

    @Test
    public void whenLoginCalled_Successfull_Endpoint() {
        String expectedResponse = "Success";
        String response = loginEndpoint.login(userLoginDto);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void whenLoginCalled_Successfull_Service() {
        String expectedResponse = "Success";
        String response = userService.login(userLoginDto);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void whenUserFoundByEmail_Successfull() {
        String email = "user@email.com";
        ApplicationUser user = userRepository.findUserByEmail(email);
        assertNotNull(user);
        assertEquals(email, user.getEmail());
    }
}
