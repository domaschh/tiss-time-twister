package at.ac.tuwien.sepr.groupphase.backend.integrationtest;

import at.ac.tuwien.sepr.groupphase.backend.TissRoom;
import at.ac.tuwien.sepr.groupphase.backend.service.TissRoomLocationServiceInter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class TissLocationServiceTests {
    @Autowired
    private TissRoomLocationServiceInter locationService;

    @Test
    void validRoomReturnsAddress() {
        String roomName = "HS 17 Friedrich Hartmann - ARCH";

        TissRoom room = locationService.fetchCorrectLocation(roomName);
        assertEquals(room.address(), "Karlsplatz 13, Stiege 7, 3. Stock");
    }
}
