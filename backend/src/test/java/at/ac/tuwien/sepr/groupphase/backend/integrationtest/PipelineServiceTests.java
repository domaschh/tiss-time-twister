package at.ac.tuwien.sepr.groupphase.backend.integrationtest;

import at.ac.tuwien.sepr.groupphase.backend.service.PipelineService;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.component.VEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class PipelineServiceTests {
    private static final String TISS_URL = "https://tiss.tuwien.ac.at/events/rest/calendar/personal?locale=de&token=5c144bcb-eebb-46f6-8825-f111f796dabc";

    @Autowired
    private PipelineService calendarService;

    @Test
    void removesAllFunktionaleProgrammierungEvents() throws ParserException, IOException, URISyntaxException {
        var returnedCalendar = calendarService.pipeCalendar(TISS_URL);
        var numberOfFProgEvents = returnedCalendar
            .getComponentList()
            .getAll()
            .stream()
            .filter(VEvent.class::isInstance)
            .filter(c -> ((VEvent) c).getSummary().get().getValue().equals("194.026 VU Funktionale Programmierung")).collect(
                Collectors.toList()).size();
        assertEquals(0, numberOfFProgEvents);
        assertEquals(264, returnedCalendar
            .getComponentList()
            .getAll()
            .size());
    }
}
