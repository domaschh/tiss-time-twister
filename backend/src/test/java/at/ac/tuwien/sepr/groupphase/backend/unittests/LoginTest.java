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
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test", "generateData"})
class LoginTest implements TestData {

    private static  final String SAMPLE_EMAIL = "user2@email.com";
    private static final String SAMPLE_PASSWORD = "password";
    @Autowired
    private LoginEndpoint loginEndpoint;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDataGenerator userDataGenerator;
    @Autowired
    private UserService userService;
    private UserLoginDto userLoginDto;

    @BeforeEach
    public void setUp() {
        userDataGenerator.generateUsers();
        userLoginDto = new UserLoginDto();
        userLoginDto.setEmail(SAMPLE_EMAIL);
        userLoginDto.setPassword(SAMPLE_PASSWORD);
    }

    @Test
    void whenLoginCalled_Successfull_Endpoint() {
        String expectedResponse = "Bearer";
        String response = loginEndpoint.login(userLoginDto);
        assertTrue(response.toString().contains(expectedResponse));
    }

    @Test
    void whenLoginCalled_Successfull_Service() {
        String expectedResponse = "Bearer";
        String response = userService.login(userLoginDto);
        assertTrue(response.toString().contains(expectedResponse));
    }

    @Test
    void whenUserFoundByEmail_Successfull() {
        String email = "user2@email.com";
        ApplicationUser user = userRepository.findUserByEmail(email);
        assertNotNull(user);
        assertEquals(email, user.getEmail());
    }
}