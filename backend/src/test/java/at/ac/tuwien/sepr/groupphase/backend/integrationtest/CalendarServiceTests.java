package at.ac.tuwien.sepr.groupphase.backend.integrationtest;


import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.service.CalendarService;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CalendarServiceTests {
    private static final String TISS_URL = "https://tiss.tuwien.ac.at/events/rest/calendar/personal?locale=de&token=5c144bcb-eebb-46f6-8825-f111f796dabc";

    @Autowired
    private CalendarService calendarService;

    @Test
    public void fetchCalendarByUrl() throws ParserException, IOException {
        Calendar calendar = calendarService.fetchCalendarByUrl(TISS_URL);

        var list = calendar.getComponentList();
        list.getAll().forEach(System.out::println);
        assertNotNull(calendar);
    }

    @Test
    public void fetchCalendarByUrl_InvalidUrl_Fails() {
        assertThrows(IOException.class, () -> {
            var calendar = calendarService.fetchCalendarByUrl("https://url.com");
        });
    }
}
