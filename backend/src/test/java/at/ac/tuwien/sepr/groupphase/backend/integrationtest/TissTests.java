package at.ac.tuwien.sepr.groupphase.backend.integrationtest;

import at.ac.tuwien.sepr.groupphase.backend.LVADetail;
import at.ac.tuwien.sepr.groupphase.backend.TissRoom;
import at.ac.tuwien.sepr.groupphase.backend.service.TissService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class TissTests {

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
    private TissService tissService;

    @Test
    void validRoomReturnsAddress() {
        String roomName = "HS 17 Friedrich Hartmann - ARCH";
        TissRoom room = tissService.fetchCorrectLocation(roomName);
        assertEquals(room.address(), "Karlsplatz 13, Stiege 7, 3. Stock");
    }

    @Test
    void validateRoomShortHand() {
        String roomName = "PR Programmiertechniken für Visual Computing";
        LVADetail shortHand = tissService.mapLVANameShorthand(roomName);
        assertEquals(shortHand.shorthand(), "PR PVC");
    }

    @Test
    void validateRoomShortHandMaster() {
        String roomName = "VU Computability Theory";
        LVADetail shortHand = tissService.mapLVANameShorthand(roomName);
        assertEquals(shortHand.shorthand(), "VU CT");
    }

    @Test
    void validateRoomShortHand2() {
        String roomName = "VU Funktionale Programmierung";
        LVADetail shortHand = tissService.mapLVANameShorthand(roomName);
        assertEquals(shortHand.shorthand(), "VU FP");
        assertEquals(shortHand.examURl(), "https://tiss.tuwien.ac.at/education/course/examDateList.xhtml?courseNr=194026&semester=2023W");
    }
}